package org.demo.post.repository.post_queue;

import org.demo.post.ui.dto.GetPostContentResponseDto;

import java.util.List;

public interface UserPostQueueQueryRepository {

    List<GetPostContentResponseDto> getPostContentList(Long userId, Long lastPostId);
}
