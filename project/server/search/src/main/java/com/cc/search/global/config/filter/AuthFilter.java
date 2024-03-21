package com.cc.business.global.config.filter;

import com.cc.business.global.common.request.CustomHttpServletRequestWrapper;
import com.cc.business.global.common.request.CustomMultipartFileRequest;
import com.cc.business.global.config.filter.openfeign.AuthOpenFeign;
import com.cc.business.global.exception.auth.AuthErrorCode;
import com.cc.business.global.exception.auth.AuthException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

@Slf4j
public class AuthFilter implements Filter {

    private final AuthOpenFeign authOpenFeign;

    public AuthFilter(AuthOpenFeign authOpenFeign) {
        this.authOpenFeign = authOpenFeign;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        String contentType = req.getContentType();
        log.info("contentType : "+ contentType);

        HttpServletRequest request = (HttpServletRequest) req;  // downCasting
        String Authorization = request.getHeader("Authorization");
        String Authorization_refresh = request.getHeader("Authorization_refresh");

        log.info("Authorization : "+Authorization);
        log.info("Authorization_refresh : "+Authorization_refresh);

        ResponseEntity<Integer> responseEntity;

        try{
            responseEntity = authOpenFeign.connectToAuthServer(Authorization,Authorization_refresh);
        } catch(Exception e){

            System.out.println(e.getMessage());
            List<String> parts = List.of(e.getMessage().split("]: "));
            String message = getCodeFromAuthResponse(parts.get(1));
            System.out.println(message);

            switch (message) {
                case "올바른 형태의 토큰이 아닙니다." -> throw new AuthException(AuthErrorCode.NOT_PROPER_TOKEN);
                case "만료된 토큰입니다." -> throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
                case "일치하는 회원이 없습니다." -> throw new AuthException(AuthErrorCode.NOT_FOUND_USER);
                case "리프레쉬토큰에 해당하는 회원이 없습니다." -> throw new AuthException(AuthErrorCode.NOT_FOUND_USER_REFRESH);
                default -> throw new AuthException(AuthErrorCode.INTERNAL_AUTH_SERVER_ERROR);
            }
        }

        String acceess_new = responseEntity.getHeaders().getFirst("Authorization");
        String refresh_new = responseEntity.getHeaders().getFirst("Authorization_refresh");

        if(refresh_new != null) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.addHeader("Authorization", acceess_new);  // 새로운 Authorization 헤더 값 추가
            response.addHeader("Authorization_refresh", refresh_new);  // 새로운 Authorization_refresh 헤더 값 추가
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            int memberId = responseEntity.getBody();
            log.info("memberId : " + memberId);

            if (contentType != null && contentType.startsWith("multipart")){
                CustomMultipartFileRequest custommultiRequest = new CustomMultipartFileRequest(request, memberId);
                chain.doFilter(custommultiRequest, res);
            } else {
                CustomHttpServletRequestWrapper customRequest = new CustomHttpServletRequestWrapper(request, memberId);
                chain.doFilter(customRequest, res);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

//    public int isAuthorized(HttpServletRequest request) {
//        String Authorization = request.getHeader("Authorization");
//        String AuthorizationRefresh = request.getHeader("Authorization_refresh");
//        int memberId = 0;
//
//        try {
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!");
//            ResponseEntity<Integer> responseEntity = authOpenFeign.connectToAuthServer(Authorization,AuthorizationRefresh);
//            System.out.println("responseEnttity " + responseEntity);
//            System.out.println("zzzzz     " + responseEntity.getHeaders());
//            String acceess_new = responseEntity.getHeaders().getFirst("Authorization");
//            String refresh_new = responseEntity.getHeaders().getFirst("Authorization_refresh");
//            System.out.println("access : " + acceess_new);
//            System.out.println("refresh : " + refresh_new);
//            return 999;
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//            List<String> parts = List.of(e.getMessage().split("]: "));
//            String message = getCodeFromAuthResponse(parts.get(1));
//            System.out.println(message);
//            if (message.equals("올바른 형태의 토큰이 아닙니다.")){
//                throw new AuthException(AuthErrorCode.NOT_PROPER_TOKEN);
//            } else if (message.equals("만료된 토큰입니다.")) {
//                throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
//            } else if (message.equals("일치하는 회원이 없습니다.")){
//                throw new AuthException(AuthErrorCode.NOT_FOUND_USER);
//            } else if (message.equals("리프레쉬토큰에 해당하는 회원이 없습니다.")){
//                throw new AuthException(AuthErrorCode.NOT_FOUND_USER_REFRESH);
//            } else {
//                throw new AuthException(AuthErrorCode.INTERNAL_AUTH_SERVER_ERROR);
//            }
//        }
//    }

    public String getCodeFromAuthResponse(String jsonResponse) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
//            int code = jsonNode.get(0).get("code").asInt();  // 만약 메시지가 아니라 코드로 소통할 경우..
            String message = jsonNode.get(0).get("message").asText();

            return message;
        }catch(JsonProcessingException e){
            return "error";
        }
    }
}
