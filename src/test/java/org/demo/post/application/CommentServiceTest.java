package org.demo.post.application;

import org.demo.post.application.dto.LikeRequestDto;
import org.demo.post.application.dto.UpdateCommentRequestDto;
import org.demo.post.domain.Post;
import org.demo.post.domain.comment.Comment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentServiceTest extends PostApplicationTestTemplate{

    @Test
    void givenCreateCommentRequestDto_whenCreateComment_thenReturnComment() {
        // when
        Comment comment = commentService.createComment(commentRequestDto);

        // then
        assertEquals(comment.getContent(), comment.getContent());
    }

    @Test
    void givenCreateComment_whenUpdateComment_thenReturnUpdatedComment() {
        // given
        Comment comment = commentService.createComment(commentRequestDto);

        // when
        UpdateCommentRequestDto dto = new UpdateCommentRequestDto(user.getId(), "updated content");
        Comment updatedComment = commentService.updateComment(comment.getId(), dto);

        // then
        assertEquals(comment.getId(), updatedComment.getId());
        assertEquals(comment.getAuthor(), updatedComment.getAuthor());
        assertEquals(comment.getContent(), updatedComment.getContent());
    }

    @Test
    void givenComment_whenLiked_thenReturnCommentWithLikeCountIncreased() {
        // given
        Comment comment = commentService.createComment(commentRequestDto);

        // when
        commentService.likeComment(new LikeRequestDto(comment.getId(), otherUser.getId()));

        // then
        assertEquals(1, comment.getLikeCount());
    }

    @Test
    void givenComment_whenLikedTwice_thenReturnCommentWithLikeCountIncreased() {
        // given
        Comment comment = commentService.createComment(commentRequestDto);

        // when
        commentService.likeComment(new LikeRequestDto(comment.getId(), otherUser.getId()));
        commentService.likeComment(new LikeRequestDto(comment.getId(), otherUser.getId()));

        // then
        assertEquals(1, comment.getLikeCount());
    }
}
