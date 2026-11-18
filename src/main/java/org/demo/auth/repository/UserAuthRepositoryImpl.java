package org.demo.auth.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.demo.auth.application.interfaces.UserAuthRepository;
import org.demo.auth.domain.UserAuth;
import org.demo.auth.repository.entity.UserAuthEntity;
import org.demo.auth.repository.jpa.JpaUserAuthRepository;
import org.demo.user.application.Interface.UserRepository;
import org.demo.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {


    private final JpaUserAuthRepository jpaUserAuthRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserAuth registerUser(UserAuth auth, User user) {
        User savedUser = userRepository.save(user);
        UserAuthEntity userAuthEntity = new UserAuthEntity(auth, savedUser.getId());
        userAuthEntity = jpaUserAuthRepository.save(userAuthEntity);
        return userAuthEntity.toUserAuth();
    }

    @Override
    public UserAuth loginUser(String email, String password) {
        UserAuthEntity userAuthEntity = jpaUserAuthRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보가 존재하지 않습니다."));
        UserAuth userAuth = userAuthEntity.toUserAuth();

        if (!userAuth.matchPassword(password)) {
            throw new IllegalArgumentException("패스워드가 틀립니다.");
        }

        return userAuth;
    }
}
