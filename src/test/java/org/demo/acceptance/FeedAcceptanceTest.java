package org.demo.acceptance;

import org.demo.acceptance.utils.AcceptanceTestTemplate;
import org.demo.post.application.dto.CreatePostRequestDto;
import org.demo.post.domain.content.PostPublicationState;
import org.demo.post.ui.dto.GetPostContentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.demo.acceptance.steps.FeedAcceptanceSteps.requestCreatePost;
import static org.demo.acceptance.steps.FeedAcceptanceSteps.requestFeed;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeedAcceptanceTest extends AcceptanceTestTemplate {
    /**
     * User1 --- follow ---> User2
     * User1 --- follow ---> User3
     */
    @BeforeEach
    void setUp() {
        super.init();
    }

    /**
     * User2 create Post 1
     * User1 Get Post 1 From Feed
     */
    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeed_thenFollowerCanGetPostFromFeed() {
        // given
        CreatePostRequestDto dto = new CreatePostRequestDto(2L, "user1 can get this post", PostPublicationState.PUBLIC);
        Long createdPostId = requestCreatePost(dto);

        // when
        List<GetPostContentResponseDto> result = requestFeed(1L);

        // then
        assertEquals(1, result.size());
        assertEquals(createdPostId, result.get(0).getId());
    }
}
