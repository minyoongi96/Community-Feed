package org.demo.acceptance.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.demo.auth.application.dto.CreateUserAuthRequestDto;
import org.demo.auth.application.dto.SendEmailRequestDto;
import org.demo.user.application.dto.CreateUserRequestDto;
import org.demo.user.application.dto.FollowUserRequestDto;
import org.springframework.stereotype.Component;

import static org.demo.acceptance.steps.SignUpAcceptanceSteps.*;
import static org.demo.acceptance.steps.UserAcceptanceSteps.followUser;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    public void loadData() {
        // user1, user2, user3 생성
        for(int i = 1; i < 4; i++) {
            createUser("user" + i + "@test.com");
        }

        followUser(new FollowUserRequestDto(1L, 2L));
        followUser(new FollowUserRequestDto(1L, 3L));
    }

    public String getEmailToken(String email) {
        return entityManager.createNativeQuery("SELECT token FROM community_email_verification WHERE email = ?", String.class)
                .setParameter(1, email)
                .getSingleResult()
                .toString();
    }

    public boolean isEmailVerified(String email) {
        return entityManager.createQuery("SELECT isVerified FROM EmailVerificationEntity WHERE email = :email", Boolean.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Long getUserId(String email) {
        return (Long) entityManager.createQuery("SELECT userId FROM UserAuthEntity WHERE email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public void createUser(String email) {
        requestSendEmail(new SendEmailRequestDto(email));
        String token = getEmailToken(email);
        requestVerifyEmail(email, token);
        registerUser(new CreateUserAuthRequestDto(email, "password", "USER", "test_user", ""));
    }
}
