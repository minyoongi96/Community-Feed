package org.demo.post.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.demo.post.application.Interface.CommentRepository;
import org.demo.post.domain.Post;
import org.demo.post.domain.comment.Comment;
import org.demo.post.repository.entity.comment.CommentEntity;
import org.demo.post.repository.jpa.JpaCommentRepository;
import org.demo.post.repository.jpa.JpaPostRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;
    private final JpaPostRepository jpaPostRepository;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        Post targetPost = comment.getPost();
        CommentEntity commentEntity = new CommentEntity(comment);

        if(comment.getId() != null) {
            jpaCommentRepository.updateCommentEntity(commentEntity);
            return commentEntity.toComment();
        }
        commentEntity = jpaCommentRepository.save(commentEntity);
          jpaPostRepository.increaseCommentCount(targetPost.getId());
        return commentEntity.toComment();
    }

    @Override
    public Comment findById(Long id) {

        CommentEntity commentEntity = jpaCommentRepository.findById(id).orElseThrow();
        return commentEntity.toComment();
    }
}
