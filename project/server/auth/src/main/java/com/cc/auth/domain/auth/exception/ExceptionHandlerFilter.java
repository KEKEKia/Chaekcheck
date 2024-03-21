package com.cc.auth.domain.auth.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cc.auth.global.common.response.EnvelopeResponse;
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
        } catch (JWTDecodeException e){
            System.out.println(e);
            System.out.println(e.getMessage());
            setErrorResponse(response, AuthErrorCode.NOT_PROPER_TOKEN);
        } catch (TokenExpiredException e){
            System.out.println(e);
            System.out.println(e.getMessage());
            setErrorResponse(response, AuthErrorCode.EXPIRED_TOKEN);
        } catch (IllegalArgumentException e){
            System.out.println(e);
            System.out.println(e.getMessage());
            setErrorResponse(response, AuthErrorCode.NOT_FOUND_USER);
        } catch (AuthException e){
            setErrorResponse(response, AuthErrorCode.NOT_FOUND_USER_REFRESH);
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