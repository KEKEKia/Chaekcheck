package com.cc.business.global.exception;

public class SearchException extends RuntimeException {
    public SearchException() {
        super("Search Api 에러 발생");
    }
}
