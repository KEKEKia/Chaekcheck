package com.cc.business.global.common.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;

@Getter
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */

    private final int memberId;

    public CustomHttpServletRequestWrapper(HttpServletRequest request, int memberId) {
        super(request);
        this.memberId = memberId;
    }
}
