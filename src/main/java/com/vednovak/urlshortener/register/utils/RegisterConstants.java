package com.vednovak.urlshortener.register.utils;

public class RegisterConstants {

    public static final int MIN_HTTP_STATUS_CODE = 301;
    public static final int MAX_HTTP_STATUS_CODE = 302;
    public static final int SHORTEN_URL_LENGTH = 6;
    public static final String SLASH = "/";
    public static final String SCHEME_SEPARATOR = "://";
    public static final String COLON = ":";
    public static final String USER_NOT_FOUND = "The username or password is not valid.";

    private RegisterConstants() {
        throw new IllegalStateException("Utility class");
    }
}
