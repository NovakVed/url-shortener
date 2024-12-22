package com.vednovak.urlshortener.utils;

public class RegexPatterns {

    public static final String ALPHABETIC_PATTERN = "^[A-Za-z]+$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private RegexPatterns() {
        throw new IllegalStateException("Utility class");
    }
}