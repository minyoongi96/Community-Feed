package org.demo.post.repository.post_queue;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.demo.post.repository.entity.post.PostEntity;
import org.demo.post.repository.entity.post.UserPostQueueEntity;
import org.demo.post.repository.jpa.JpaPostRepository;
import org.demo.post.repository.jpa.JpaUserPostQueueRepository;
import org.demo.user.repository.Jpa.JpaUserRelationRepository;
import org.demo.user.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserPostQueueCommandRepositoryImpl implements UserPostQueueCommandRepository{

    private final JpaPostRepository jpaPostRepository;
    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserPostQueueRepository jpaUserPostQueueRepository;

    @Override
    @Transactional
    public void publishPost(PostEntity postEntity) {
        UserEntity userEntity = postEntity.getAuthor();
        List<Long> followerIdList = jpaUserRelationRepository.findFollowers(userEntity.getId());
        List<UserPostQueueEntity> userPostQueueEntityList = followerIdList.stream()
                .map(userId -> new UserPostQueueEntity(userId, postEntity.getId(), userEntity.getId()))
                .toList();

        jpaUserPostQueueRepository.saveAll(userPostQueueEntityList);
    }

    @Override
    @Transactional
    public void saveFollowPost(Long userId, Long targetId) {
        List<Long> postIdList = jpaPostRepository.findAllIdsByAuthorId(targetId);
        List<UserPostQueueEntity> userPostQueueEntityList = postIdList.stream()
                .map(postId -> new UserPostQueueEntity(userId, postId, targetId))
                .toList();

        jpaUserPostQueueRepository.saveAll(userPostQueueEntityList);
    }

    @Override
    @Transactional
    public void deleteUnfollowPost(Long userId, Long targetId) {
        jpaUserPostQueueRepository.deleteAllByUserIdAndAuthorId(userId, targetId);
    }
}
