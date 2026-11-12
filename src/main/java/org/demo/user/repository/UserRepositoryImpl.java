package org.demo.user.repository;

import lombok.RequiredArgsConstructor;
import org.demo.user.application.Interface.UserRepository;
import org.demo.user.domain.User;
import org.demo.user.repository.Jpa.JpaUserRepository;
import org.demo.user.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user);
        entity = jpaUserRepository.save(entity);
        return entity.toUser();
    }

    @Override
    public User findById(Long id) {
        UserEntity entity = jpaUserRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return entity.toUser();
    }
}
