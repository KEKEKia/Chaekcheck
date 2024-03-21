package com.cc.business.global.common.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@Getter
public class CustomMultipartFileRequest extends StandardMultipartHttpServletRequest {

    private final int memberId;

    public CustomMultipartFileRequest(HttpServletRequest request, int memberId) throws MultipartException {
        super(request);
        this.memberId = memberId;
    }
}