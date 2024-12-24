package com.vednovak.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerApplication {

    // TODO: write unit tests / integration tests
    // https://stackoverflow.com/questions/64193818/spring-security-default-password-is-not-printing-on-console
    // https://stackoverflow.com/questions/37285016/what-is-username-and-password-when-starting-spring-boot-with-tomcat
    // TODO: add actuator for monitoring - make it work
    // TODO: migrate to postgreSQL
    // TODO: add caching
    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}
