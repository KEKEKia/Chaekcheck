package com.cc.auth.domain.auth.service.jwt.filter;

import com.cc.auth.domain.auth.dto.PrincipalDetails;
import com.cc.auth.domain.auth.exception.AuthErrorCode;
import com.cc.auth.domain.auth.exception.AuthException;
import com.cc.auth.domain.auth.service.jwt.JwtProvider.JwtProvider;
import com.cc.auth.domain.member.domain.Member;
import com.cc.auth.domain.member.repository.MemberRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationProcessingFilter extends BasicAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private static final String BEARER = "Bearer";

    public JwtAuthenticationProcessingFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtProvider.extractRefreshToken(request)
                .filter(jwtProvider::isTokenValid)
                .orElse(null);
        if(refreshToken != null){
            System.out.println("==== REFRESH ====== : ");
            System.out.println("==== REFRESH ====== : " + refreshToken);
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresentOrElse(member -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(member);
                    jwtProvider.sendAccessAndRefreshToken(response, jwtProvider.createAccessToken(member.getOauthIdentifier()),
                            reIssuedRefreshToken);
                }, ()->{throw new AuthException(AuthErrorCode.NOT_FOUND_USER_REFRESH);
                });
    }

    private String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtProvider.createRefreshToken();
        member.setRefreshToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssuedRefreshToken;
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Optional<String> token  = jwtProvider.extractAccessToken(request);

        jwtProvider.extractAccessToken(request)
                .filter(jwtProvider::isTokenValid)
                .ifPresent(accessToken -> jwtProvider.extractEmail(accessToken)
                        .ifPresent(username -> memberRepository.findByOauthIdentifier(username)
                                    .ifPresentOrElse(
                                            this::saveAuthentication,
                                            () -> {
                                                throw new IllegalArgumentException("Username not found");
                                            }
                                    )));

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(Member member) {

        PrincipalDetails principalDetails = new PrincipalDetails(member);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails, null, authoritiesMapper.mapAuthorities(principalDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        }
}
