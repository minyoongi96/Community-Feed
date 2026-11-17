package org.demo.auth.repository;

import lombok.RequiredArgsConstructor;
import org.demo.auth.application.interfaces.EmailVerificationRepository;
import org.demo.auth.domain.Email;
import org.demo.auth.repository.entity.EmailVerificationEntity;
import org.demo.auth.repository.jpa.JpaEmailVerificationRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final JpaEmailVerificationRepository jpaEmailVerificationRepository;

    @Override
    public void createEmailVerification(Email email, String token) {
        String emailText = email.getEmailText();
        Optional<EmailVerificationEntity> entity = jpaEmailVerificationRepository.findByEmail(emailText);

        if(entity.isPresent()) {
            EmailVerificationEntity emailVerificationEntity = entity.get();

            if(emailVerificationEntity.isVerified()) {
                throw new IllegalArgumentException("이미 인증된 이메일입니다.");
            }

            emailVerificationEntity.updateToken(token);
            return;
        }

        // 인증 토큰 DB 저장
        EmailVerificationEntity emailVerificationEntity = new EmailVerificationEntity(emailText, token);
        jpaEmailVerificationRepository.save(emailVerificationEntity);
    }
}

