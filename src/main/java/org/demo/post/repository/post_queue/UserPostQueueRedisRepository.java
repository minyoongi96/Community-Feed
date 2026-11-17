package org.demo.post.repository.post_queue;

import org.demo.post.repository.entity.post.PostEntity;

import java.util.List;

public interface UserPostQueueRedisRepository {

    void publishPostToFollowerList(PostEntity postEntity, List<Long> userIdList);

    void publishPostListToFollowerUser(List<PostEntity> postEntityList, Long userId);

    void deleteFeed(Long userId, Long authorId);
}
