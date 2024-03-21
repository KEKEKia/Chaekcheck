package com.cc.auth.global.config;

import com.cc.auth.domain.auth.exception.ExceptionHandlerFilter;
import com.cc.auth.domain.auth.service.PrincipalOauth2UserService;
import com.cc.auth.domain.auth.service.jwt.JwtProvider.JwtProvider;
import com.cc.auth.domain.auth.service.jwt.filter.JwtAuthenticationProcessingFilter;
import com.cc.auth.domain.auth.service.oauth2.handler.OAuth2LoginSuccessHandler;
import com.cc.auth.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint()
//                .accessDeniedHandler()
//                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                // .addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationProcessingFilter.class)
                .headers().frameOptions().disable()

                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .apply(new MyCustomDsl())

                .and()
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().permitAll()
                )

//              .antMatchers("/user/**").authenticated()
//              .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")

                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        return http.getOrBuild();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationProcessingFilter.class)
                    .addFilter(new JwtAuthenticationProcessingFilter(authenticationManager, jwtProvider, memberRepository));
        }
    }
}
