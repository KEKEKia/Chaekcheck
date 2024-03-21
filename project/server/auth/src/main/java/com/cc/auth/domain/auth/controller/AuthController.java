package com.cc.auth.domain.auth.controller;

import com.cc.auth.domain.auth.dto.LogoutResponseDto;
import com.cc.auth.domain.auth.dto.PrincipalDetails;
import com.cc.auth.domain.auth.service.AuthService;
import com.cc.auth.domain.auth.service.jwt.JwtProvider.JwtProvider;
import com.cc.auth.domain.member.domain.Member;
import com.cc.auth.global.common.response.EnvelopeResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    @PostMapping("/auth/logout")
    public ResponseEntity<EnvelopeResponse<LogoutResponseDto>> logout(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request){

        Optional<String> accessToken = jwtProvider.extractAccessToken(request);
        Member member = principalDetails.getMember();

        return new ResponseEntity<EnvelopeResponse<LogoutResponseDto>>(new EnvelopeResponse<>(HttpStatus.OK.value(), "로그아웃 완료", authService.logout(member, accessToken)), HttpStatus.OK);
    }

    @GetMapping("/authorization")
    public int authorization (HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return principalDetails.getMember().getId();
    }
}
