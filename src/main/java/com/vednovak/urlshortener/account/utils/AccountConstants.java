package com.vednovak.urlshortener.account.utils;

public class AccountConstants {

    public static final int MIN_ACCOUNT_CHARACTER_LENGTH = 3;
    public static final int MAX_CHARACTER_LENGTH = 30;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int PASSWORD_LENGTH = 8;
    public static final String CREATE_ACCOUNT_SUCCESSFUL = "create.account.successful";
    public static final String CREATE_ACCOUNT_UNSUCCESSFUL = "create.account.unsuccessful";
    public static final String USER_AUTHENTICATION_UNSUCCESSFUL = "The username or password is not valid.";

    private AccountConstants() {
        throw new IllegalStateException("Utility class");
    }
}
