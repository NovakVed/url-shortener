package com.vednovak.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerApplication {

    // TODO: write unit tests / integration tests
    // TODO: add actuator for monitoring - make it work
    // TODO: migrate to postgreSQL
    // TODO: add caching
    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}
