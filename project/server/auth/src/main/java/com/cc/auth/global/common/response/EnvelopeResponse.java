package com.cc.auth.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EnvelopeResponse<T> {

    @Builder.Default
    private int code = 200;

    @Builder.Default
    private String message = "success";

    private T data;

    public EnvelopeResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}