package org.demo.post.repository;

import org.demo.post.repository.entity.post.PostEntity;
import org.demo.post.repository.post_queue.UserPostQueueRedisRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 임시
@Repository
public class UserPostQueueRedisRepositoryImpl implements UserPostQueueRedisRepository {
    @Override
    public void publishPostToFollowerList(PostEntity postEntity, List<Long> userIdList) {

    }

    @Override
    public void publishPostListToFollowerUser(List<PostEntity> postEntityList, Long userId) {

    }

    @Override
    public void deleteFeed(Long userId, Long authorId) {

    }
}
