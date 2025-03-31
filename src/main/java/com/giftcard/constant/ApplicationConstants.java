package com.giftcard.constant;

public class ApplicationConstants {
    // API
    public static final String API_VERSION_PATH = "/api/v1";

    // Authentication
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String INVALID_CREDENTIALS = "Invalid Credentials, Please try again.";

    // User
    public static final String USER_ALREADY_EXISTS = "User already exists. Try using a new email!";
    public static final String USER_DOES_NOT_EXIST = "The user does not exist";
    public static final String SUCCESSFUL_REGISTRATION = "Successfully registered a user!";
    public static final String SUCCESSFUL_AUTHENTICATION = "User authenticated successfully!";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY = "Password changed successfully!";
    public static final String OLD_PASSWORD_MATCHES = "The old password matches the new password!";

    public static final byte USER_EXISTS_CODE = 1;
    public static final byte USER_CREATED_CODE = 2;
    public static final byte PASSWORD_CHANGED_SUCCESSFULLY_CODE = 1;
    public static final byte OLD_PASSWORD_MATCHES_CODE = 2;

    public static final String TIRANE_ZONE_ID = "Europe/Tirane";

    // Card properties
    public static final String ID_CHARACTERS = "0123456789ABCDEF";
    public static final byte CARD_ID_LENGTH = 10;

    public static final String GIFT_CARD_EMAIL_TEMPLATE = "create-card-email";
    public static final String GIFT_CARD_EMAIL_SUBJECT = "Gift card information";

    public static final String VALIDATE_CARD_EMAIL_TEMPLATE = "validate-card-email";
    public static final String VALIDATE_CARD_EMAIL_SUBJECT = "Gift card validation";
}
