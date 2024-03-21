package com.cc.business.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.http.HttpMethod;

@Slf4j
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        log.info("corsFilter 적용..");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        config.addExposedHeader("Authorization");
        config.addExposedHeader("Authorization_refresh");

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> registrationBean =
                new FilterRegistrationBean<>(corsFilter());
        registrationBean.setOrder(0); // CorsFilter를 가장 먼저 실행하도록 설정
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
