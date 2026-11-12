package org.demo.user.repository.Jpa;

import org.demo.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long>, JpaUserRepositoryCustom {
}
