package com.cc.business.global.config;

import com.cc.business.global.config.filter.AuthFilter;
import com.cc.business.global.config.filter.ExceptionHandlerFilter;
import com.cc.business.global.config.filter.openfeign.AuthOpenFeign;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final AuthOpenFeign authOpenFeign;

    public WebConfig(AuthOpenFeign authOpenFeign) {
        this.authOpenFeign = authOpenFeign;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("Authorization")
                .allowedHeaders("Authorization_refresh")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean(){
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<AuthFilter>(new AuthFilter(authOpenFeign));
        registrationBean.setOrder(2);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilterFilterRegistrationBean(){
        FilterRegistrationBean<ExceptionHandlerFilter> registrationBean = new FilterRegistrationBean<ExceptionHandlerFilter>(new ExceptionHandlerFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean(){
//        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<CorsFilter>(new CorsFilter());
//        registrationBean.setOrder(1);
//        registrationBean.addUrlPatterns("/*");
//
//        return registrationBean;
//    }
}
