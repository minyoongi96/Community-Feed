package org.demo.post.repository;

import org.demo.post.repository.post_queue.UserPostQueueQueryRepository;
import org.demo.post.ui.dto.GetPostContentResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"!test"})
public class UserPostQueueQueryRepositoryImpl implements UserPostQueueQueryRepository {
    @Override
    public List<GetPostContentResponseDto> getPostContentList(Long userId, Long lastPostId) {
        return List.of();
    }
}
