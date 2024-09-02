package com.nameplz.baedal.global.config;

import com.nameplz.baedal.global.common.jwt.JwtUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Components components = new Components();

        addPageableSchemas(components); // Pageable 스키마 추가
        addSecuritySchemes(components); // 인증 스키마 추가

        SecurityRequirement accessTokenRequirement = new SecurityRequirement().addList(JwtUtil.AUTHORIZATION_HEADER);

        return new OpenAPI()
                .info(info())
                .components(components)
                .addSecurityItem(accessTokenRequirement);
    }

    private void addPageableSchemas(Components components) {

        Schema<?> properties = new MapSchema()
                .properties(Map.of(
                        "page", new IntegerSchema().example(1),
                        "size", new IntegerSchema().example(10),
                        "sort", new StringSchema().example("createdAt,desc")
                ));

        components.addSchemas("Pageable", properties);
    }

    private void addSecuritySchemes(Components components) {
        components.addSecuritySchemes(JwtUtil.AUTHORIZATION_HEADER, accessTokenSecurityScheme());
    }

    private SecurityScheme accessTokenSecurityScheme() {
        return new SecurityScheme()
                .name(JwtUtil.AUTHORIZATION_HEADER) // Authorization 을 키로 함
                .type(SecurityScheme.Type.APIKEY) // name 값을 키로 함. (HTTP 설정 시 Authorization 고정이지만 확장 차)
                .in(SecurityScheme.In.COOKIE) // 액세스 토큰은 헤더에 속함
                .scheme(JwtUtil.BEARER_PREFIX) // Bearer 접두사 붙음
                .bearerFormat("JWT"); // 유형은 JWT
    }

    private Info info() {
        return new Info()
                .title("2제 팀이름을 정해조 API 명세서")
                .version("0.0.1");
    }
}
