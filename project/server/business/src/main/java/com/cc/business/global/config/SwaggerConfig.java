package com.cc.business.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//http://localhost:8080/swagger-ui/index.html

@OpenAPIDefinition(
        info = @Info(title = "쳌책",
                description = "쳌책 api명세",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/business/**"};

        return GroupedOpenApi.builder()
                .group("CheckCheack API v1")
                .pathsToMatch(paths)
                .build();
    }
}