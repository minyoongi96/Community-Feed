package org.demo.post.domain.comment;

import org.demo.post.domain.Post;
import org.demo.post.domain.content.CommentContent;
import org.demo.post.domain.content.PostContent;
import org.demo.user.domain.User;
import org.demo.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CommentTest {

    private final User author = new User(1L, new UserInfo("author", null));
    private final User otherUser = new User(2L, new UserInfo("other", null));
    private final Post post = new Post(1L, author, new PostContent("test post"));
    private final Comment comment = new Comment(1L, post, author, new CommentContent("test comment"));

    @Test
    void givenCommentCreated_whenLike_thenLikeCountShouldBe1() {
        // when
        comment.like(otherUser);

        // then
        assert(comment.getLikeCount() == 1);
    }

    @Test
    void givenCommentCreated_whenLikeAndUnlike_thenLikeCountShouldBe0() {
        // when
        comment.like(otherUser);
        comment.unlike(otherUser);

        // then
        assert(comment.getLikeCount() == 0);
    }

    @Test
    void givenCommentCreated_whenLikeByAuthor_thenThrowsError() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> comment.like(author));
    }

    @Test
    void givenCommentCreated_whenUnlikeByAuthor_thenThrowsError() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> comment.unlike(author));
    }

    @Test
    void givenCommentCreated_whenUpdateByAuthor_thenContentShouldBeUpdated() {
        // when
        String updatedContent = "updated content";
        comment.updateComment(author, updatedContent);

        // then
        assert(comment.getContent().equals(updatedContent));
    }

    @Test
    void givenCommentCreated_whenUpdateByOtherUser_thenThrowsError() {
        String updatedContent = "updated content";

        // when, then
        assertThrows(IllegalArgumentException.class, () -> comment.updateComment(otherUser, updatedContent));
    }

    @Test
    void givenCommentCreated_whenGetLikeCount_thenReturnsLikeCount() {
        // when
        int likeCount = comment.getLikeCount();

        // then
        assert(likeCount == 0);
    }

    @Test
    void givenCommentLengthIsOver_Limit_whenCreateComment_thenThrowsError() {
        // given
        String longContent = "a".repeat(101);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new Comment(1L, post, author, new CommentContent(longContent)));
    }
}
