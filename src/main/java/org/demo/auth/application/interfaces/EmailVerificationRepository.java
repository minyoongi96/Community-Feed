package org.demo.auth.application.interfaces;

import org.demo.auth.domain.Email;

public interface EmailVerificationRepository {

    void createEmailVerification(Email email, String token);
    void verifyEmail(Email email, String token);
    boolean isEmailVerified(Email email);
}
