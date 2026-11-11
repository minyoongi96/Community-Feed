package org.demo.post.repository.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.demo.common.domain.PositiveIntegerCounter;
import org.demo.common.repository.entity.TimeBaseEntity;
import org.demo.post.domain.Post;
import org.demo.post.domain.content.PostContent;
import org.demo.post.domain.content.PostPublicationState;
import org.demo.user.repository.entity.UserEntity;

@Entity
@Table(name = "community_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "authorId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    private String content;
    private Integer likeCount;

    @Convert(converter = PostPublicationStateConverter.class)
    private PostPublicationState state;

    public PostEntity(Post post) {
        this.id = post.getId();
        this.author = new UserEntity(post.getAuthor());
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.state = post.getState();
    }

    public Post toPost() {
        return Post.builder()
                .id(id)
                .author(author.toUser())
                .content(new PostContent(content))
                .likeCount(new PositiveIntegerCounter(likeCount))
                .state(state)
                .build();
    }



}
