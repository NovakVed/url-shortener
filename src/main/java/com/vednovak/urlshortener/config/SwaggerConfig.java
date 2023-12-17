package com.vednovak.urlshortener.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApiInformation() {
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Localhost Server URL");

        Contact contact = new Contact()
                .email("vednovak@gmail.com")
                .name("Vedran Novak");

        Info info = new Info()
                .contact(contact)
                .title("URL Shortener API")
                .description("""
                        URL Shortener API: Shorten and manage long URLs efficiently for convenient sharing and tracking.
                        Build the Spring Boot application with maven: mvn clean package (in case error: "command not found: mvn" please install maven -> macOS: brew install maven)
                        Run the Spring boot application with the java -jar <jar_file_name> command: java -jar target/url-shortener-0.0.1-SNAPSHOT.jar""")
                .summary("URL Shortener API: Shorten and manage long URLs efficiently for convenient sharing and tracking.")
                .version("V1.0.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));

        return new OpenAPI().info(info).addServersItem(localServer);
    }
}
