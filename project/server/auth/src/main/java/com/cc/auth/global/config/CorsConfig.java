package com.cc.auth.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

   @Value("${jwt.access.header}")
   String accessHeader;
   @Value("${jwt.refresh.header}")
   String refreshHeader;

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(false);
      config.addAllowedOrigin("*"); // e.g. http://domain1.com
      config.addAllowedHeader("*");
      config.addAllowedMethod("*");
      config.addExposedHeader(accessHeader);
      config.addExposedHeader(refreshHeader);

      source.registerCorsConfiguration("/**", config);

      // FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<>(new CorsFilter(source));
      // filterBean.setOrder(0);

      return new CorsFilter(source);
   }

}
