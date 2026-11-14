package org.demo.user.repository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.demo.post.repository.jpa.JpaUserPostQueueRepository;
import org.demo.post.repository.post_queue.UserPostQueueCommandRepository;
import org.demo.user.application.Interface.UserRelationRepository;
import org.demo.user.domain.User;
import org.demo.user.repository.Jpa.JpaUserRelationRepository;
import org.demo.user.repository.Jpa.JpaUserRepository;
import org.demo.user.repository.entity.UserEntity;
import org.demo.user.repository.entity.UserRelationEntity;
import org.demo.user.repository.entity.UserRelationIdEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRelationRepositoryImpl implements UserRelationRepository {

    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserRepository jpaUserRepository;
    private final UserPostQueueCommandRepository userPostQueueCommandRepository;

    @Override
    public boolean isAlreadyFollow(User user, User targetUser) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUser.getId());
        return jpaUserRelationRepository.existsById(id);
    }

    @Override
    @Transactional
    public void save(User user, User targetUser) {
        UserRelationEntity entity = new UserRelationEntity(user.getId(), targetUser.getId());
        jpaUserRelationRepository.save(entity);
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
        userPostQueueCommandRepository.saveFollowPost(user.getId(), targetUser.getId());
    }

    @Override
    @Transactional
    public void delete(User user, User targetUser) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUser.getId());
        jpaUserRelationRepository.deleteById(id);
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
        userPostQueueCommandRepository.deleteUnfollowPost(user.getId(), targetUser.getId());
    }
}
