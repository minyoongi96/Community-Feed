package org.demo.post.application;

import org.demo.fake.FakeObjectFactory;
import org.demo.post.application.dto.CreatePostRequestDto;
import org.demo.post.application.dto.LikeRequestDto;
import org.demo.post.domain.Post;
import org.demo.post.domain.content.PostPublicationState;
import org.demo.user.application.UserService;
import org.demo.user.application.dto.CreateUserRequestDto;
import org.demo.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostServiceTest {

    private final UserService userService = FakeObjectFactory.getUserService();
    private final PostService postService = FakeObjectFactory.getPostService();

    private final User user = userService.createUser(new CreateUserRequestDto("user1", ""));
    private final User otherUser = userService.createUser(new CreateUserRequestDto("user2", ""));

    private final CreatePostRequestDto dto = new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC);

    @Test
    void givenPostRequestDto_whenCreatePost_thenReturnPost() {
        // when
        Post savedPost = postService.createPost(dto);

        // then
        Post post = postService.getPost(savedPost.getId());
        assertEquals(savedPost, post);
    }

    @Test
    void givenCreatedPost_whenUpdatePost_thenReturnUpdatedPost() {
        // given
        Post savedPost = postService.createPost(dto);

        // when
        String updatedContent = "updated content";
        CreatePostRequestDto updateDto = new CreatePostRequestDto(user.getId(), updatedContent, PostPublicationState.PUBLIC);
        Post updatedPost = postService.updatePost(savedPost.getId(), updateDto);

        // then
        assertEquals(savedPost.getId(), updatedPost.getId());
        assertEquals(savedPost.getAuthor(), updatedPost.getAuthor());
        assertEquals(updatedContent, updatedPost.getContent());
    }

    @Test
    void givenCreatedPost_whenLiked_thenReturnPostWithLikeCountIncreased() {
        // given
        Post savedPost = postService.createPost(dto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, savedPost.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenLikedTwice_thenReturnPostWithLikeCountIncreased() {
        // given
        Post savedPost = postService.createPost(dto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, savedPost.getLikeCount());
    }

    @Test
    void givenLikedPost_whenUnliked_thenReturnPostWithLikeCountDecreased() {
        // given
        Post savedPost = postService.createPost(dto);
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // when
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, savedPost.getLikeCount());
    }

    @Test
    void givenLikedPost_whenUnlikedTwice_thenReturnPostWithLikeCountDecreased() {
        // given
        Post savedPost = postService.createPost(dto);
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // when
        postService.unlikePost(likeRequestDto);
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, savedPost.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenUnlike_thenReturnPostWithoutLike() {
        // given
        Post savedPost = postService.createPost(dto);
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());

        // when
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, savedPost.getLikeCount());
    }
}
