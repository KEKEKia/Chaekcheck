package com.cc.business.global.config.filter;

import com.cc.business.global.common.response.EnvelopeResponse;
import com.cc.business.global.exception.auth.AuthErrorCode;
import com.cc.business.global.exception.auth.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (AuthException e){
            String message = e.getErrorCode().getMessage();
            switch (message) {
                case "올바른 형태의 토큰이 아닙니다." -> setErrorResponse(response, AuthErrorCode.NOT_PROPER_TOKEN);
                case "만료된 토큰입니다." -> setErrorResponse(response, AuthErrorCode.EXPIRED_TOKEN);
                case "일치하는 회원이 없습니다." -> setErrorResponse(response, AuthErrorCode.NOT_FOUND_USER);
                case "리프레쉬토큰에 해당하는 회원이 없습니다." -> setErrorResponse(response, AuthErrorCode.NOT_FOUND_USER_REFRESH);
                default -> setErrorResponse(response, AuthErrorCode.INTERNAL_AUTH_SERVER_ERROR);
            }
        }
    }

    private void setErrorResponse(HttpServletResponse response, AuthErrorCode authErrorCode){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(authErrorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        EnvelopeResponse<?> envelopeResponse = EnvelopeResponse.builder().code(authErrorCode.getHttpStatus().value())
                .message(authErrorCode.getMessage()).build();

        try {
            response.getWriter().write(objectMapper.writeValueAsString(envelopeResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}