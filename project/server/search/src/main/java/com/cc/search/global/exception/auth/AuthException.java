package com.cc.business.global.exception.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException{
    private final AuthErrorCode errorCode;
}
