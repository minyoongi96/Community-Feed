package org.demo.post.application;

import org.demo.post.application.Interface.LikeRepository;
import org.demo.post.application.Interface.PostRepository;
import org.demo.post.application.dto.CreatePostRequestDto;
import org.demo.post.application.dto.LikeRequestDto;
import org.demo.post.application.dto.UpdatePostRequestDto;
import org.demo.post.domain.Post;
import org.demo.post.domain.content.Content;
import org.demo.post.domain.content.PostContent;
import org.demo.user.application.UserService;
import org.demo.user.domain.User;

public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, LikeRepository likeRepository, UserService userService) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userService = userService;
    }

    public Post getPost(Long id) {

        return postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
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

    public void likePost(LikeRequestDto dto) {
        User user = userService.getUser(dto.userId());
        Post post = getPost(dto.targetId());

        if(likeRepository.checkLike(post, user)) {
            throw new IllegalArgumentException();
        }

        post.like(user);
        likeRepository.save(post, user);
    }

    public void unlikePost(LikeRequestDto dto) {
        User user = userService.getUser(dto.userId());
        Post post = getPost(dto.targetId());

        if(likeRepository.checkLike(post, user)) {
            post.unlike(user);
            likeRepository.unlike(post, user);
        }
    }
}
