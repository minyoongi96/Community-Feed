package org.demo.auth.domain;

import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email createEmail(String email) {
        if (isNotValidEmailString(email)) {
            throw new IllegalArgumentException("Email is not Valid");
        }

        return new Email(email);
    }

    public String getEmailText() {
        return this.email;
    }

    private static boolean isNotValidEmailString(String email) {
        return email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email).matches();
    }
}
