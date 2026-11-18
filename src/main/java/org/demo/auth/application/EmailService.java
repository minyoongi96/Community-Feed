package org.demo.auth.application;

import lombok.RequiredArgsConstructor;
import org.demo.auth.application.dto.SendEmailRequestDto;
import org.demo.auth.application.interfaces.EmailSendRepository;
import org.demo.auth.application.interfaces.EmailVerificationRepository;
import org.demo.auth.domain.Email;
import org.demo.auth.domain.RandomTokenGenerator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailSendRepository emailSendRepository;

    public void sendEmail(SendEmailRequestDto dto) {
        Email email = Email.createEmail(dto.email());
        String token = RandomTokenGenerator.generateToken();

        // 인증 토큰 DB 저장
        emailVerificationRepository.createEmailVerification(email, token);

        // 인증 메일 발송
        emailSendRepository.sendEmail(email, token);
    }

    public void verifyEmail(String email, String token) {
        Email emailValue = Email.createEmail(email);
        emailVerificationRepository.verifyEmail(emailValue, token);
    }
}
