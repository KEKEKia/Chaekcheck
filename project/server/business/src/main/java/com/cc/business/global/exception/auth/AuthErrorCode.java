package com.cc.business.global.exception.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "일치하는 회원이 없습니다."),
    NOT_PROPER_TOKEN(HttpStatus.UNAUTHORIZED, "올바른 형태의 토큰이 아닙니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    NOT_FOUND_USER_REFRESH(HttpStatus.NOT_FOUND, "리프레쉬토큰에 해당하는 회원이 없습니다."),
    INTERNAL_AUTH_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "인증과정에서 에러 발생");

    private final HttpStatus httpStatus;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}