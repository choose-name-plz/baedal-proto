package com.nameplz.baedal.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(info())
                .components(new Components());
    }

    private Info info() {
        return new Info()
                .title("2제 팀이름을 정해조 API 명세서")
                .version("0.0.1");
    }
}
