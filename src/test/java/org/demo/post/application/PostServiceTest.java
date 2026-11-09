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

class PostServiceTest extends PostApplicationTestTemplate{

    @Test
    void givenPostRequestDto_whenCreatePost_thenReturnPost() {

        // then
        Post savedPost = postService.getPost(post.getId());
        assertEquals(savedPost, post);
    }

    @Test
    void givenCreatedPost_whenUpdatePost_thenReturnUpdatedPost() {

        // when
        String updatedContent = "updated content";
        CreatePostRequestDto updateDto = new CreatePostRequestDto(user.getId(), updatedContent, PostPublicationState.PUBLIC);
        Post updatedPost = postService.updatePost(post.getId(), updateDto);

        // then
        assertEquals(post.getId(), updatedPost.getId());
        assertEquals(post.getAuthor(), updatedPost.getAuthor());
        assertEquals(updatedContent, updatedPost.getContent());
    }

    @Test
    void givenCreatedPost_whenLiked_thenReturnPostWithLikeCountIncreased() {
        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(post.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, post.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenLikedTwice_thenReturnPostWithLikeCountIncreased() {

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(post.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, post.getLikeCount());
    }

    @Test
    void givenLikedPost_whenUnliked_thenReturnPostWithLikeCountDecreased() {
        // given
        LikeRequestDto likeRequestDto = new LikeRequestDto(post.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // when
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenLikedPost_whenUnlikedTwice_thenReturnPostWithLikeCountDecreased() {
        // given
        LikeRequestDto likeRequestDto = new LikeRequestDto(post.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // when
        postService.unlikePost(likeRequestDto);
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenUnlike_thenReturnPostWithoutLike() {
        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(post.getId(), otherUser.getId());
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, post.getLikeCount());
    }
}
