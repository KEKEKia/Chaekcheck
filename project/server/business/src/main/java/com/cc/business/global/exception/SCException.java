package com.cc.business.global.exception;

public class SCException extends RuntimeException {
    public SCException() {
        super("책의 상태 분류 시 에러가 발생했습니다.");
    }
}
