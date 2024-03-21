package com.cc.business.domain.service;// BusinessServiceImpl.java
import com.amazonaws.services.mq.model.NotFoundException;
import com.amazonaws.services.mq.model.UnauthorizedException;
import com.cc.business.global.config.filter.openfeign.AuthOpenFeign;
import com.cc.business.domain.dto.*;
import com.cc.business.domain.entity.BookEntity;
import com.cc.business.domain.entity.BookImageEntity;
import com.cc.business.domain.repository.BookImageRepository;
import com.cc.business.domain.repository.BookRepository;
import com.cc.business.global.exception.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

    @Value("${server.url}")
    String SERVER_URL;

    @Value("${ta.port}")
    String TA_PORT;

    @Value("${search.port}")
    String SEARCH_PORT;

    @Value("${sc.port}")
    String SC_PORT;

    @Value("${ans.port}")
    String ANS_PORT;

    private BookRepository bookRepository;
    private BookImageRepository bookImageRepository;
    private AuthOpenFeign authOpenFeign;
    private S3Service s3Service;

    public BusinessServiceImpl(BookRepository bookRepository, BookImageRepository bookImageRepository, AuthOpenFeign authOpenFeign, S3Service s3Service) {
        this.bookRepository = bookRepository;
        this.bookImageRepository = bookImageRepository;
        this.authOpenFeign = authOpenFeign;
        this.s3Service = s3Service;
    }

    @Override
    @Transactional
    public BookDto processImages(HttpServletRequest request, List<MultipartFile> imageList, int memberId) throws IOException {

        BookDto result = new BookDto();

        log.info("사용자 ID: {}", memberId);

        log.info("이미지 정보 요청값: {}", imageList);
        /* S3에 이미지 저장 */
        List<String> imageUrlList = s3Service.upload(imageList);
        log.info("S3 이미지 저장 경로 {}", imageUrlList);

        /* 이미지에서 text 추출 */
        List<String> textList = new ArrayList<>();
        try {
            textList = getImageText(imageUrlList);
        } catch(Exception e) {
            log.error("글자 추출 시 에러 발생");
            throw new TAException();
        }

        /* 추출된 글자를 이용하여 책 정보 검색 */
        AladinResponseDto aladinResponse = getBookInfo(textList);
        if(aladinResponse == null) {
            result = null;
        } else {
            result.setTitle(aladinResponse.getTitle());
            result.setAuthor(aladinResponse.getAuthor());
            result.setPublisher(aladinResponse.getPublisher());
            result.setImage(aladinResponse.getCover());

        }

        /* step1. 책 정보 먼저 저장 */
        int bookId = saveBookInfo(result, memberId);
        log.info("책 번호: {}", bookId);
        result.setBookId(bookId);

        /* step2. 저장된 책의 id를 가지고 이미지 정보를 저장 */
        saveS3URL(imageUrlList, bookId);
        return result;
    }

    @Override
    public List<String> getImageText(List<String> imageUrlList) {
        log.info("이미지에서 글자 추출 실행 {}", imageUrlList);

        // webClient 기본 설정
        WebClient webClient = WebClient
                .builder()
//                .baseUrl(SERVER_URL + ":" + TA_PORT)
                .baseUrl(SERVER_URL)
                .build();

        HashMap<String, String> request = new HashMap<>();
        request.put("img_url", imageUrlList.get(0));

        // api 요청
        List<String> response = webClient
                .post()
                .uri("/ta/abstraction")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(List.class)
                .block();
        log.info("TA 결과: {}", response);
        return response;
    }

    // search
    @Override
    public AladinResponseDto getBookInfo(List<String> textList) {
        log.info("추출된 글자로 책 정보 검색");
        // webClient 기본 설정
        WebClient webClient = WebClient
                .builder()
//                .baseUrl(SERVER_URL + ":" + SEARCH_PORT)
                .baseUrl(SERVER_URL)
                .build();

        HashMap<String, List<String>> request = new HashMap<>();
        request.put("textList", textList);

        // api 요청
        AladinResponseDto response = webClient
                .post()
                .uri("/search/bookinfo")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AladinResponseDto.class)
                .block();
        log.info("Search 결과: {}", response);
        return response;
    }

    @Override
    @Transactional
    public HashMap<String, Object> processPredictBookInfo(HttpServletRequest request, HashMap<String, BookDto> params, int memberId) throws JsonProcessingException {
        log.info("책 가격 예측 프로세스 시작");
        log.info("사용자 아이디: {}", memberId);
        BookDto editedBookInfo = params.get("bookInfo");
        log.info("수정된 책 정보 요청값: {}", params.get("bookInfo"));

        /* 책ID를 이용하여 S3에 저장된 이미지 리스트 호출  */
        List<String> imageUrlList = getImageUrlList(editedBookInfo.getBookId());
        log.info("S3에 저장된 이미지 목록: {}", imageUrlList);

        /* 수정된 책 정보를 이용하여 다시 알라딘 API 검색 */
        BookEntity certainBookInfo = null;
        try {
            certainBookInfo = searchCertainBookInfo(editedBookInfo);
            log.info("정확한 책 정보: {}", certainBookInfo);
        } catch(Exception e) {
            log.error("certainBook 검색 시 에러 발생");
            throw new SearchException();
        }


        SCDto scDto = null;
        if(certainBookInfo == null) {
            /* 책 정보 못찾으면 기존에 저장했던 이미지들 삭제 해야할듯? */

        } else {
            /* imageUrlList를 이용하여 책의 상태 반환 */
            try {
                scDto = getImageStatus(imageUrlList);
                log.info("책의 상태: {}", scDto);
                certainBookInfo.setStatus(scDto.getStatus());
            } catch(Exception e) {
                log.error("책의 상태 분류 중 에러 발생");
                throw new SCException();
            }

            /* 책의 상태를 이용하여 재평가된 책의 가격 반환 */
            int bookPrice = 0;
            try {
                bookPrice = getBookPrice(certainBookInfo, scDto);
                log.info("재평가된 책의 가격: {}", bookPrice);
                certainBookInfo.setEstimatedPrice(bookPrice);
            } catch(Exception e) {
                log.error("책의 예상 가격 분석 중 에러 발생");
                throw new AnsException();
            }

            /* 재검색된 책의 정보 DB에 저장 */
            certainBookInfo.setBookId(editedBookInfo.getBookId());
            certainBookInfo.setMemberId(memberId);
            saveCertainBookInfo(certainBookInfo);
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("certainBookInfo", certainBookInfo);
        data.put("scInfo", scDto);

        log.info("최종 데이터: {}", data);
        return data;
    }

    // sc
    @Override
    public SCDto getImageStatus(List<String> imageUrlList) throws JsonProcessingException {
//        String url = "http://j9a606.p.ssafy.io:8085/sc/bookstatus";
        String url = "https://j9a606.p.ssafy.io/sc/bookstatus";

        // restTemplete 생성
        RestTemplate restTemplate = new RestTemplate();
        
        // 헤더 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // 바디 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.addAll("image_list", imageUrlList);

        // reqeust 메세지 생성
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        // 
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        SCDto status = objectMapper.readValue(response.getBody(), SCDto.class);

        return status;
    }

    // ans
    @Override
    public int getBookPrice(BookEntity certainBookInfo, SCDto scDto) {
        // webClient 기본 설정
        WebClient webClient = WebClient
                .builder()
//                .baseUrl(SERVER_URL + ":" + ANS_PORT)
                .baseUrl(SERVER_URL)
                .build();

        HashMap<String, Object> request = new HashMap<>();
        request.put("publish_year", certainBookInfo.getPubDate());
        request.put("origin_price", certainBookInfo.getOriginalPrice());
        request.put("all_data", scDto.getAll());
        request.put("back_data", scDto.getBack());
        request.put("cover_data", scDto.getCover());
        request.put("side_data", scDto.getSide());

        // api 요청
        int response = webClient
                .post()
                .uri("/analysis/bookprice")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
//        log.info("Ans 결과: {}", response);
        return response;
    }

    @Override
    public int saveBookInfo(BookDto bookInfo, int memberId) {
        BookEntity bookEntity = new BookEntity().builder()
                .title(bookInfo.getTitle())
                .publisher(bookInfo.getPublisher())
                .author(bookInfo.getAuthor())
                .coverImage(bookInfo.getImage())
                .memberId(memberId)
                .build();
        return  bookRepository.save(bookEntity).getBookId();
    }

    @Override
    public void saveS3URL(List<String> imageUrlList, int bookId) {
        for(int i=0; i<imageUrlList.size(); i++) {
            BookImageEntity bookImageEntity = new BookImageEntity().builder()
                    .imagePath("checkchaeck")
                    .imageUrl(imageUrlList.get(i))
                    .bookId(bookId)
                    .build();
            bookImageRepository.save(bookImageEntity);
        }
    }

    @Override
    public List<String> getImageUrlList(int bookId) {
        List<BookImageEntity> response = bookImageRepository.findAllByBookId(bookId);
        List<String>  imageUrlList = new ArrayList<>();
        for(int i=0; i<response.size(); i++) {
            imageUrlList.add(response.get(i).getImageUrl());
        }
        return imageUrlList;
    }

    @Override
    public BookEntity searchCertainBookInfo(BookDto bookInfo) {
        // webClient 기본 설정
        WebClient webClient = WebClient
                .builder()
//                .baseUrl(SERVER_URL + ":" + SEARCH_PORT)
                .baseUrl(SERVER_URL)
                .build();

        // api 요청
        AladinResponseDto response = webClient
                .post()
                .uri("/search/certainbookinfo")
                .bodyValue(bookInfo)
                .retrieve()
                .bodyToMono(AladinResponseDto.class)
                .block();
//        log.info("Search 결과: {}", response);

        BookEntity result = new BookEntity();
        if(response == null) {
            result = null;
        } else {
            AladinResponseDto arDto;
            ObjectMapper mapper = new ObjectMapper();
            arDto = mapper.convertValue(response, AladinResponseDto.class);

            result.setTitle(arDto.getTitle());
            result.setAuthor(arDto.getAuthor());
            result.setPublisher(arDto.getPublisher());
            result.setCoverImage(arDto.getCover());
            result.setOriginalPrice(arDto.getPriceStandard());
            result.setPubDate(arDto.getPubDate().substring(0,4));
        }
        return result;
    }

    @Override
    public FindHistoriesResponseDto findHistories(int memberId) {
        List<BookEntity> books = bookRepository.findAllByMemberId(memberId);
        return FindHistoriesResponseDto.of(books);
    }

    @Override
    public FindHistoryResponseDto findHistory(int memberId, Long bookId) {
        Optional<BookEntity> bookEntityOptional = bookRepository.findById(bookId);
        BookEntity book  = bookEntityOptional.orElseThrow(()-> new NotFoundException("해당 책이 없습니다."));

        return FindHistoryResponseDto.of(book);
    }

    @Override
    public FindHistoriesResponseDto searchHistory(int memberId, String keyword) {
        List<BookEntity> books = bookRepository.searchAllByMemberId(memberId, keyword);
        return FindHistoriesResponseDto.of(books);
    }

    public void saveCertainBookInfo(BookEntity certainBookInfo) {
        log.info("저장할 값: {}", certainBookInfo);
        bookRepository.save(certainBookInfo);
    }

    @Override
    public Long deleteHistory(int memberId, Long bookId) {

        BookEntity book = bookRepository.findById(bookId).get();
        if (book.getMemberId() != memberId){
            throw new UnauthorizedException("삭제권한없음");
        }
        bookRepository.deleteById(bookId);

        return bookId;
    }
}
