package org.demo.post.application.Interface;

import org.demo.post.domain.Post;
import org.demo.post.domain.comment.Comment;
import org.demo.user.domain.User;

public interface LikeRepository {

    boolean checkLike(Post post, User user);
    void save(Post post, User user);
    void unlike(Post post, User user);

    boolean checkLike(Comment comment, User user);
    void save(Comment comment, User user);
    void unlike(Comment comment, User user);
}
