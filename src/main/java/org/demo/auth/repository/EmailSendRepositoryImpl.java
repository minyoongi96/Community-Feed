package org.demo.auth.repository;

import org.demo.auth.application.interfaces.EmailSendRepository;
import org.demo.auth.domain.Email;
import org.springframework.stereotype.Repository;

@Repository
public class EmailSendRepositoryImpl implements EmailSendRepository {
    @Override
    public void sendEmail(Email email, String token) {
        // TODO : 구글 SMTP
    }
}
