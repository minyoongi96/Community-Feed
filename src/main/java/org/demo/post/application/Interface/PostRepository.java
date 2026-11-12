package org.demo.post.application.Interface;

import org.demo.post.domain.Post;

public interface PostRepository {
    Post save(Post post);

    Post findById(Long id);
}
