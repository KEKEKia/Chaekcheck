package com.cc.auth.global.advice;

import com.cc.auth.domain.auth.exception.AuthException;
import com.cc.auth.global.common.response.EnvelopeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<EnvelopeResponse<?>> AuthExceptionHandler(AuthException e){

        e.printStackTrace();

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(EnvelopeResponse.builder()
                        .code(e.getErrorCode().getHttpStatus().value())
                        .message(e.getErrorCode().getMessage())
                        .build());
    }
}