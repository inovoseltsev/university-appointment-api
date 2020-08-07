package com.novoseltsev.appointmentapi.validation.message;

public class ErrorMessageUtil {

    private static final String BASE_MESSAGE_PART = " is not correct!";

    public static final String FIRST_NAME_ERROR =
            "User last name" + BASE_MESSAGE_PART;

    public static final String LAST_NAME_ERROR =
            "User first name" + BASE_MESSAGE_PART;

    public static final String LOGIN_ERROR =
            "User login" + BASE_MESSAGE_PART;

    public static final String EMAIL_ERROR =
            "User email" + BASE_MESSAGE_PART;

    public static final String PASSWORD_ERROR =
            "User password" + BASE_MESSAGE_PART;

    public static final String REPEATED_PASSWORD_ERROR =
            "User repeated password" + BASE_MESSAGE_PART;
}
