package org.demo.post.repository.post_queue;

import org.demo.post.repository.entity.post.PostEntity;

public interface UserPostQueueCommandRepository {

    void publishPost(PostEntity postEntity);

    void saveFollowPost(Long userId, Long targetId);

    void deleteUnfollowPost(Long userId, Long targetId);
}
