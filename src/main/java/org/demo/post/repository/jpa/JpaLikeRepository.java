package org.demo.post.repository.jpa;

import org.demo.post.repository.entity.like.LikeEntity;
import org.demo.post.repository.entity.like.LikeEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, LikeEntityId> {
}
