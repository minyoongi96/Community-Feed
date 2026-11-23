package org.demo.post.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.demo.message.application.interfaces.MessageRepository;
import org.demo.post.application.Interface.LikeRepository;
import org.demo.post.domain.Post;
import org.demo.post.domain.comment.Comment;
import org.demo.post.repository.entity.like.LikeEntity;
import org.demo.post.repository.jpa.JpaCommentRepository;
import org.demo.post.repository.jpa.JpaLikeRepository;
import org.demo.post.repository.jpa.JpaPostRepository;
import org.demo.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final JpaPostRepository jpaPostRepository;
    private final JpaCommentRepository jpaCommentRepository;
    private final JpaLikeRepository jpaLikeRepository;
    private final MessageRepository messageRepository;

    @Override
    public boolean checkLike(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);
        return jpaLikeRepository.existsById(likeEntity.getId());
    }

    @Override
    @Transactional
    public void like(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);
        entityManager.persist(likeEntity);
        jpaPostRepository.updateLikeCount(post.getId(), 1);
        messageRepository.sendLikeMessage(user, post.getAuthor());
    }

    @Override
    @Transactional
    public void unlike(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);
        jpaLikeRepository.deleteById(likeEntity.getId());
        jpaPostRepository.updateLikeCount(post.getId(), -1);
    }

    @Override
    public boolean checkLike(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
        return jpaLikeRepository.existsById(likeEntity.getId());
    }

    @Override
    @Transactional
    public void like(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
        entityManager.persist(likeEntity);
        jpaCommentRepository.updateLikeCount(comment.getId(), 1);
    }

    @Override
    @Transactional
    public void unlike(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
        jpaLikeRepository.deleteById(likeEntity.getId());
        jpaCommentRepository.updateLikeCount(comment.getId(), -1);
    }
}
