package org.demo.post.repository.jpa;

import org.demo.post.repository.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {

    @Modifying
    @Query(value = "UPDATE PostEntity p " +
            "SET p.content = :#{#post.getContent()}, " +
            "p.state = :#{#post.getState()}, " +
            "p.updDt = now() " +
            "WHERE p.id = :#{#post.getId()}")
    void updatePostEntity(PostEntity post);

    @Modifying
    @Query(value = "UPDATE PostEntity p " +
            "SET p.content = :#{#post.getContent()}, " +
            "p.likeCount = :#{#post.getLikeCount()}, " +
            "p.updDt = now() " +
            "WHERE p.id = :#{#post.getId()}")
    void updateLikeCount(PostEntity post);

    @Modifying
    @Query(value = "UPDATE PostEntity p " +
            "SET p.commentCount = p.commentCount + 1, " +
            "p.updDt = now() " +
            "WHERE p.id = :id")
    void increaseCommentCount(Long id);
}
