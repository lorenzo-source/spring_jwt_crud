package it.dynacode.javaJwtCRUD.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new ProcessHandle.Info()
                        .title("JWT CRUD API")
                        .description("Secured REST API with JWT authentication and user management")
                        .version("1.0.0"));
    }
}