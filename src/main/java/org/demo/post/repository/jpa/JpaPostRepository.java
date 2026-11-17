package org.demo.post.repository.jpa;

import org.demo.post.repository.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {

    @Query(value = "select p from PostEntity p where p.author.id = :authorId")
    List<PostEntity> findAllByAuthorId(Long authorId);

    @Modifying
    @Query(value = "UPDATE PostEntity p " +
            "SET p.content = :#{#post.getContent()}, " +
            "p.state = :#{#post.getState()}, " +
            "p.updDt = now() " +
            "WHERE p.id = :#{#post.getId()}")
    void updatePostEntity(PostEntity post);

    @Modifying
    @Query(value = "UPDATE PostEntity p " +
            "SET p.likeCount = p.likeCount + :likeCount, " +
            "p.updDt = now() " +
            "WHERE p.id = :postId")
    void updateLikeCount(Long postId, Integer likeCount);

    @Modifying
    @Query(value = "UPDATE PostEntity p " +
            "SET p.commentCount = p.commentCount + 1, " +
            "p.updDt = now() " +
            "WHERE p.id = :id")
    void increaseCommentCount(Long id);
}
