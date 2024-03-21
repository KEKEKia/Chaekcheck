package com.cc.business.global.config.filter.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="AuthOpenFeign", url = "http://j9a606.p.ssafy.io:8081")
public interface AuthOpenFeign {

    @GetMapping("/authorization")
    ResponseEntity<Integer> connectToAuthServer(
            @RequestHeader String Authorization,
            @RequestHeader String Authorization_refresh
    );
}