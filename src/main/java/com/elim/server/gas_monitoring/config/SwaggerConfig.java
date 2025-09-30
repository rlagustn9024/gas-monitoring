package com.elim.server.gas_monitoring.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    /**
     * [기본 설정]
     * <p> 서버, JWT 인증 스키마... </p>
     * */
    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("API 문서")
                .version("v1")
                .description("API 테스트용 문서");

        SecurityScheme bearerAuth = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);

//        // 공통 오류 응답 등록
        Components components = new Components()
                .addSecuritySchemes("bearerAuth", bearerAuth);

        return new OpenAPI()
                .info(info)
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("로컬 서버"),
                        new Server().url("http://192.168.0.224:8081").description("내부 서버")
                ))
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(components);
    }


    // ============================= API 그룹 설정 =============================




//    private ApiResponse createResponse(String description) {
//        return new ApiResponse()
//                .description(description)
//                .content(new Content().addMediaType("application/json",
//                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
//                ));
//    }

//    @PostConstruct
//    public void registerSwaggerSnakeCaseConverter() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
//        ModelConverters.getInstance().addConverter(new ModelResolver(objectMapper));
//    }
}
