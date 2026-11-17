package org.demo.acceptance;

import org.demo.acceptance.utils.AcceptanceTestTemplate;
import org.demo.post.application.dto.CreateCommentRequestDto;
import org.demo.post.application.dto.CreatePostRequestDto;
import org.demo.post.domain.content.PostPublicationState;
import org.demo.post.ui.dto.GetContentResponseDto;
import org.demo.post.ui.dto.GetPostContentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.demo.acceptance.steps.FeedAcceptanceSteps.*;
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
     * User2 create 1 Post
     * User1 Get 1 Post From Feed
     */
    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeed_thenFollowerCanGetPostFromFeed() {
        // given
        Long user1Id = 1L;
        Long user2Id = 2L;

        CreatePostRequestDto dto = new CreatePostRequestDto(user2Id, "user1 can get this post", PostPublicationState.PUBLIC);
        Long createdPostId = requestCreatePost(dto);

        // when
        List<GetPostContentResponseDto> result = requestFeed(user1Id);

        // then
        assertEquals(1, result.size());
        assertEquals(createdPostId, result.get(0).getId());
    }

    /**
     * User1 create 1 Comment at Post1
     * User2 Get 1 Comment From Post1
     */
    @Test
    void givenUser1CreateCommentAtPost1_whenUser2RequestComments_thenUser2CanGetCommentFromPost1() {
        // given
        Long user1Id = 1L;
        Long user2Id = 2L;

        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto(user2Id, "user1 can get this post", PostPublicationState.PUBLIC);
        Long createdPostId = requestCreatePost(createPostRequestDto);

        CreateCommentRequestDto createCommentRequestDto = new CreateCommentRequestDto(createdPostId, user1Id, "this is a user1's test comment");
        Long createdCommentId = requestCreateComment(createCommentRequestDto);

        // when
        List<GetContentResponseDto> result = requestComment(user2Id, createdPostId);

        // then
        assertEquals(1, result.size());
        assertEquals(createdCommentId, result.get(0).getId());
    }
}
