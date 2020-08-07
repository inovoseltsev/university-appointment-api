package com.novoseltsev.appointmentapi.validation.regexp;

public class PatternUtil {
    public static final String NAME_PATTERN =
            "^[a-zA-Zа-яА-я]+$";

    public static final String LOGIN_PATTERN =
            "^[a-z0-9_.-]{3,16}$";

    public static final String EMAIL_PATTERN =
            "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$";

    public static final String PASSWORD_PATTERN =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16}$";
}
