package com.oficinadobrito.api.utils.validators;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private EmailValidator() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {return false;}

        return EMAIL_PATTERN.matcher(email).matches();
    }
}
