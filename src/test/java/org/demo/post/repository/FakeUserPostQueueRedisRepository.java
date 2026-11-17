package org.demo.post.repository;

import org.demo.post.repository.entity.comment.CommentEntity;
import org.demo.post.repository.entity.post.PostEntity;
import org.demo.post.repository.post_queue.UserPostQueueRedisRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("test")
public class FakeUserPostQueueRedisRepository implements UserPostQueueRedisRepository {

    private final Map<Long, Set<PostEntity>> queue = new HashMap<>();

    @Override
    public void publishPostToFollowerList(PostEntity postEntity, List<Long> userIdList) {
        for (Long userId : userIdList) {
            if (queue.containsKey(userId)) {
                queue.get(userId).add(postEntity);
            } else {
                queue.put(userId, new HashSet<>(List.of(postEntity)));
            }
        }
    }

    @Override
    public void publishPostListToFollowerUser(List<PostEntity> postEntityList, Long userId) {
        if (queue.containsKey(userId)) {
            queue.get(userId).addAll(postEntityList);
        } else {
            queue.put(userId, new HashSet<>(postEntityList));
        }
    }

    @Override
    public void deleteFeed(Long userId, Long authorId) {
        if(queue.containsKey((userId))) {
            queue.get(userId).removeIf(post -> post.getAuthor().getId().equals(authorId));
        }
    }

    public List<PostEntity> getPostListByUserId(Long userId) {
        return List.copyOf(queue.get(userId));
    }
}
