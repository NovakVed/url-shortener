package com.vednovak.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerApplication {

    // TODO: write unit tests / integration tests
    // TODO: restructure / refactor stuff
    // TODO: add exceptions ex. @ExceptionHandler(MethodArgumentNotValidException.class) that will be handled in some central exception handler @RestControllerAdvice
    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}
