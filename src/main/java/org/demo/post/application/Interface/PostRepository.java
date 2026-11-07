package org.demo.post.application.Interface;

import org.demo.post.domain.Post;

import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long id);
}
