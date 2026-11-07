package org.demo.post.application;

import org.demo.post.application.Interface.PostRepository;
import org.demo.post.application.dto.CreatePostRequestDto;
import org.demo.post.application.dto.UpdatePostRequestDto;
import org.demo.post.domain.Post;
import org.demo.post.domain.content.Content;
import org.demo.post.domain.content.PostContent;
import org.demo.user.application.UserService;
import org.demo.user.domain.User;

public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post getPost(Long id) {

        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    public Post createPost(CreatePostRequestDto dto) {
        User user = userService.getUser(dto.userId());
        Content content = new PostContent(dto.content());
        Post post = new Post(null, user, content);
        postRepository.save(post);

        return post;
    }

    public Post updatePost(UpdatePostRequestDto dto) {
        User user = userService.getUser(dto.userId());
        Post post = getPost(dto.postId());
        post.updatePost(user, dto.content(), dto.state());

        return postRepository.save(post);
    }
}
