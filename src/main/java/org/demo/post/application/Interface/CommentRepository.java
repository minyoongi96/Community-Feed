package org.demo.post.application.Interface;

import org.demo.post.domain.comment.Comment;

import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Comment findById(Long id);
}
