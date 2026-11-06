package org.demo.post.domain;

import org.demo.post.domain.content.PostContent;
import org.demo.post.domain.content.PostPublicationState;
import org.demo.user.domain.User;
import org.demo.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    private final User author = new User(1L, new UserInfo("author", null));
    private final User otherUser = new User(2L, new UserInfo("other", null));
    private final Post post = new Post(1L, author, new PostContent("test post"));

    @Test
    void givenPostCreated_whenLike_thenLikeCountSouldbe1() {
        // when
        post.like(otherUser);

        // then
        assertEquals(1, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenLikeAndUnlike_thenLikeCountShouldBe0() {
        // when
        post.like(otherUser);
        post.unlike(otherUser);

        // then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenLikeByAuthor_thenThrowsError() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> post.like(author));
    }

    @Test
    void givenPostCreated_whenUnlikeByAuthor_thenThrowsError() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> post.unlike(author));
    }

    @Test
    void givenPostCreated_whenUpdateByAuthor_thenContentShouldBeUpdated() {
        // given
        String updatedContent = "updated content";

        // when
        post.updatePost(author, updatedContent, PostPublicationState.PUBLIC);

        // then
        assertEquals(updatedContent, post.getContent());
    }

    @Test
    void givenPostCreated_whenUpdateByOtherUser_thenThrowsError() {
        // given
        String updatedContent = "updated content";

        // when, then
        assertThrows(IllegalArgumentException.class, () -> post.updatePost(otherUser, updatedContent, PostPublicationState.PUBLIC));
    }
}
