package org.demo.auth.application.interfaces;

import org.demo.auth.domain.Email;

public interface EmailSendRepository {

    void sendEmail(Email email, String token);
}
