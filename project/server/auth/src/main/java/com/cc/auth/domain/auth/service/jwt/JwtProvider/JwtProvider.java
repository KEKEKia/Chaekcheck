package com.cc.auth.domain.auth.service.jwt.JwtProvider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.cc.auth.domain.auth.dto.TokenMapping;
import com.cc.auth.domain.member.domain.Member;
import com.cc.auth.domain.member.repository.MemberRepository;
import com.cc.auth.global.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class JwtProvider {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERNAME_CLAIM = "username";
    private static final String BEARER = "Bearer ";

    public TokenMapping createToken(String username){
        return TokenMapping.builder()
                .accessToken(createAccessToken(username))
                .refreshToken(createRefreshToken())
                .build();
    }

    public String createAccessToken(String username){
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(USERNAME_CLAIM, username)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken(){
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken){
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
//        System.out.printf("재발급된 AccessToken : {}", accessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken){
        response.setStatus(HttpServletResponse.SC_OK);
        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
//        System.out.println("Access Token, Refresh Token 헤더 설정 완료");
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractEmail(String accessToken){
        try {

            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(USERNAME_CLAIM)
                    .asString());
        } catch (Exception e) {
            System.out.println("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    @Transactional
    public void updateRefreshToken(String username, String refreshToken) {

        Optional<Member> tempMember =memberRepository.findByOauthIdentifier(username);
        tempMember.ifPresent(member -> member.setRefreshToken(refreshToken));

    }

    public boolean isTokenValid(String token){

        try{
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            redisUtil.hasKeyBlackList(token);
            if(redisUtil.hasKeyBlackList(token)){
                throw new RuntimeException("이미 로그아웃된 토큰입니다.");
            }
            return true;
        } catch(Error e){
            System.out.println("유효하지않은 토큰입니다. {}"+ e.getMessage());
            return false;
        }
    }

    public Long getExpiration(String accessToken){
        Date expiration = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(accessToken).getExpiresAt();
        long now = new Date().getTime();
        System.out.println("now "+ now);
        System.out.println("expiration.getTime() - now "+ (expiration.getTime() - now));
        return expiration.getTime() - now;
//        Date expiration = Jwts.parserBuilder().setSigningKey(JwtProperties.SECRET.getBytes())
//                .build().parseClaimsJws(accessToken).getBody().getExpiration();
    }
}
