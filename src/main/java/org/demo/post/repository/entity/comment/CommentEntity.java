package org.demo.post.repository.entity.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.demo.common.domain.PositiveIntegerCounter;
import org.demo.post.domain.comment.Comment;
import org.demo.post.domain.content.CommentContent;
import org.demo.post.repository.entity.post.PostEntity;
import org.demo.user.repository.entity.UserEntity;

@Entity
@Table(name = "community_comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "postId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PostEntity post;

    private String content;
    private Integer likeCount;

    public CommentEntity(Comment comment) {
        this.author = new UserEntity(comment.getAuthor());
        this.post = new PostEntity(comment.getPost());
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
    }

    public Comment toComment() {
        return Comment.builder()
                .id(id)
                .author(author.toUser())
                .post(post.toPost())
                .content(new CommentContent(content))
                .likeCount(new PositiveIntegerCounter(likeCount))
                .build();
    }
}
