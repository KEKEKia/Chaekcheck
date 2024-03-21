package com.cc.business.global.exception;

public class TAException extends RuntimeException {
    public TAException() {
        super("글자 추출 시 에러가 발생했습니다.");
    }
}
