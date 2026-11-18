package org.demo.acceptance;

import org.demo.acceptance.utils.AcceptanceTestTemplate;
import org.demo.post.application.dto.CreatePostRequestDto;
import org.demo.post.domain.content.PostPublicationState;
import org.demo.post.ui.dto.GetPostContentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.demo.acceptance.steps.FeedAcceptanceSteps.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedAcceptanceTest extends AcceptanceTestTemplate {

    private String token;

    /**
     * User1 --- follow ---> User2
     * User1 --- follow ---> User3
     */
    @BeforeEach
    void setUp() {
        super.init();
        this.token = login("user1@test.com");   // user1 로그인
    }

    /**
     * User2 create 1 Post
     * User1 Get 1 Post From Feed
     */
    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeed_thenFollowerCanGetPostFromFeed() {
        // given
        Long user2Id = 2L;

        CreatePostRequestDto dto = new CreatePostRequestDto(user2Id, "user1 can get this post", PostPublicationState.PUBLIC);
        Long createdPostId = requestCreatePost(dto);

        // when
        List<GetPostContentResponseDto> result = requestFeed(token);

        // then
        assertEquals(1, result.size());
        assertEquals(createdPostId, result.get(0).getId());
    }

    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeedWithInvalidToken_thenFollowerCanGetPostFromFeed() {

        // when
        Integer code = requestFeedCode("abcd");   // 잘못된 토큰으로 피드 요청

        // then
        assertEquals(400, code);
    }

    /**
     * User1 create 1 Comment at Post1
     * User2 Get 1 Comment From Post1
     */
}
