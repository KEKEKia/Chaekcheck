package com.cc.business.domain.service;// BusinessService.java
import com.cc.business.domain.dto.*;
import com.cc.business.domain.entity.BookEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface BusinessService {
    // 회원 인증
    // 이미지 프로세스(TA, SEARCH) 전체를 담당
    BookDto processImages(HttpServletRequest request, List<MultipartFile> imageList, int memberId) throws IOException;

    // TA 서버에 이미지 정보를 전송하고
    List<String> getImageText(List<String> imageList);

    // search 서버에 텍스트를 입력하면 검색 결과를 반환
    AladinResponseDto getBookInfo(List<String> imageList);

    // 책 예상 가격 프로세스(SC, ANS) 전체를 담당
    HashMap<String, Object> processPredictBookInfo(HttpServletRequest request, HashMap<String, BookDto> params, int memberId) throws JsonProcessingException;

    // sc 서버에 이미지 정보를 전송하면 상태 반환
    SCDto getImageStatus(List<String> imageUrlList) throws JsonProcessingException;

    // ans 서버에 상태와 가격을 전송하면 가격 결과 반환;
    int getBookPrice(BookEntity certainBookInfo, SCDto scDto);

    // 책 정보 DB에 저장
    int saveBookInfo(BookDto bookInfo, int memberId);

    // S3에 저장된 이미지 경로 DB에 저장
    void saveS3URL(List<String> imageUrlList, int bookId);

    // 특정 멤버의 특정 책에 대한 S3에 저장된 이미지 검색
    List<String> getImageUrlList(int bookId);

    // 정확한 책 제목, 저자, 출판사를 이용하여 알라딘 검색 API 호출
    BookEntity searchCertainBookInfo(BookDto bookInfo);

    FindHistoriesResponseDto findHistories(int memberId);
    FindHistoryResponseDto findHistory(int memberId, Long bookId);

    FindHistoriesResponseDto searchHistory(int memberId, String keyword);

    // 정확하게 검색된 책의 정보를 DB에 저장
    void saveCertainBookInfo(BookEntity certainBookInfo);

    Long deleteHistory(int memberId, Long bookId);
}
