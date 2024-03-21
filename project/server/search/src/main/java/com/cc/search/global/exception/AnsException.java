package com.cc.business.global.exception;

public class AnsException extends RuntimeException {
    public AnsException() {
        super("책 가격 예측 중 에러가 발생했습니다.");
    }
}
