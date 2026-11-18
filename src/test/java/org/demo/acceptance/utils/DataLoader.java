package org.demo.acceptance.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.demo.user.application.dto.CreateUserRequestDto;
import org.demo.user.application.dto.FollowUserRequestDto;
import org.springframework.stereotype.Component;

import static org.demo.acceptance.steps.UserAcceptanceSteps.createUser;
import static org.demo.acceptance.steps.UserAcceptanceSteps.followUser;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    public void loadData() {
        CreateUserRequestDto dto1 = new CreateUserRequestDto("test_user1", "");
        CreateUserRequestDto dto2 = new CreateUserRequestDto("test_user2", "");
        CreateUserRequestDto dto3 = new CreateUserRequestDto("test_user3", "");
        createUser(dto1);
        createUser(dto2);
        createUser(dto3);

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
        return (Long) entityManager.createNativeQuery("SELECT userId FROM community_user_auth WHERE email = ?", Long.class)
                .setParameter(1, email)
                .getSingleResult();
    }
}
