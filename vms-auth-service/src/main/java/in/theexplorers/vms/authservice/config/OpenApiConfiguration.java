package in.theexplorers.vms.authservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for Swagger documentation.
 * Provides API metadata visible in Swagger UI.
 */
@Configuration
public class OpenApiConfiguration {

    /**
     * Configures OpenAPI metadata for Auth Service.
     */
    @Bean
    public OpenAPI authServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VMS Auth Service API")
                        .description("Authentication and Authorization service for Visitor Management System")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Md Wasif Ali")
                                .email("support@theexplorers.in")
                        )
                        .license(new License()
                                .name("The Explorers License")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://theexplorers.in"));
    }
}