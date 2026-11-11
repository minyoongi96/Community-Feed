package org.demo.post.repository.entity.like;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.demo.post.domain.Post;
import org.demo.post.domain.comment.Comment;
import org.demo.user.domain.User;

@Entity
@Table(name = "community_like")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikeEntity {

    @EmbeddedId
    private LikeEntityId id;

    public LikeEntity(Post post, User user) {
        this.id = new LikeEntityId(post.getId(), user.getId(), LikeTarget.POST.name());
    }

    public LikeEntity(Comment comment, User user) {
        this.id = new LikeEntityId(comment.getId(), user.getId(), LikeTarget.COMMENT.name());
    }
}
