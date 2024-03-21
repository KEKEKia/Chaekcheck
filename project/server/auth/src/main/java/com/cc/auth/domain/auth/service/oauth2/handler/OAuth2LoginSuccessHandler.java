package com.cc.auth.domain.auth.service.oauth2.handler;

import com.cc.auth.domain.auth.dto.PrincipalDetails;
import com.cc.auth.domain.auth.dto.TokenMapping;
import com.cc.auth.domain.auth.service.jwt.JwtProvider.JwtProvider;
import com.cc.auth.domain.member.domain.Member;
import com.cc.auth.domain.member.domain.OauthType;
import com.cc.auth.domain.member.repository.MemberRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String TOKEN = "token";
    private static final String REFRESH_TOKEN = "refreshToken";

    @Value("${spring.security.oauth2.redirectUrl}")
    private String REDIRECT_URL;

    @Value("${spring.security.oauth2.redirectUrl_local}")
    private String REDIRECT_URL_LOCAL;

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//        PrincipalDetails principalDetails =(PrincipalDetails)authentication.getPrincipal();
//        loginSuccess(response, principalDetails);
        TokenMapping tokenMapping = saveUser(authentication);

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        OauthType oauthType = principal.getMember().getOauthType();
        
        String nickname = principal.getMember().getNickname();

        response.sendRedirect(getRedirectUrl(tokenMapping, nickname, oauthType));
    }

    private TokenMapping saveUser(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        TokenMapping token = jwtProvider.createToken(username);
        Member member  = memberRepository.findByOauthIdentifier(username).get();
        member.setRefreshToken(token.getRefreshToken());
        memberRepository.save(member);
        return token;
    }

    private String getRedirectUrl(TokenMapping token, String nickname, OauthType oauthType) throws UnsupportedEncodingException {

        String encodedNickname = URLEncoder.encode(nickname, "UTF-8");
        String redirect_url = null;
        if(oauthType.equals(OauthType.GOOGLE)){
            redirect_url = REDIRECT_URL;
        }else if (oauthType.equals(OauthType.KAKAO)){
            redirect_url = REDIRECT_URL_LOCAL;
        }
        return UriComponentsBuilder.fromUriString(redirect_url)
                .queryParam("nickname", encodedNickname)
                .queryParam(TOKEN, token.getAccessToken())
                .queryParam(REFRESH_TOKEN, token.getRefreshToken())
                .build().toUriString();
    }

    private void loginSuccess(HttpServletResponse response, PrincipalDetails principalDetails) throws IOException {

        String accessToken = jwtProvider.createAccessToken(principalDetails.getUsername());
        String refreshToken = jwtProvider.createRefreshToken();
        response.addHeader(jwtProvider.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtProvider.getRefreshHeader(), "Bearer " + refreshToken);

        jwtProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtProvider.updateRefreshToken(principalDetails.getUsername(), refreshToken);
    }
}
