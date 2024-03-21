package com.cc.business.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EnvelopeResponse<T> {

    private int code ;

    private String message;

    private T data;

    public EnvelopeResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
