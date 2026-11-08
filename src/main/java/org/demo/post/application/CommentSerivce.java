package org.demo.post.application;

import org.demo.post.application.Interface.CommentRepository;
import org.demo.post.application.Interface.LikeRepository;
import org.demo.post.application.dto.CreateCommentRequestDto;
import org.demo.post.application.dto.LikeRequestDto;
import org.demo.post.application.dto.UpdateCommentRequestDto;
import org.demo.post.domain.Post;
import org.demo.post.domain.comment.Comment;
import org.demo.post.domain.content.CommentContent;
import org.demo.user.application.UserService;
import org.demo.user.domain.User;

public class CommentSerivce {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    private final LikeRepository likeRepository;

    public CommentSerivce(CommentRepository commentRepository, UserService userService, PostService postService, LikeRepository likeRepository) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.likeRepository = likeRepository;
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
    }

    public Comment createComment(CreateCommentRequestDto dto) {
        User user = userService.getUser(dto.userId());
        Post post = postService.getPost(dto.postId());

        Comment comment = new Comment(null, post, user, new CommentContent(dto.content()));

        return commentRepository.save(comment);
    }

    public Comment updateComment(UpdateCommentRequestDto dto){
        User user = userService.getUser(dto.userId());
        Comment comment = getComment(dto.commentId());

        comment.updateComment(user, dto.content());

        return commentRepository.save(comment);
    }

    public void likeComment(LikeRequestDto dto){
        User user = userService.getUser(dto.userId());
        Comment comment = getComment(dto.targetId());

        if(likeRepository.checkLike(comment, user)){
            throw new IllegalArgumentException();
        }

        comment.like(user);
        likeRepository.save(comment, user);
    }

    public void unlikeComment(LikeRequestDto dto){
        User user = userService.getUser(dto.userId());
        Comment comment = getComment(dto.targetId());

        if(likeRepository.checkLike(comment, user)){
            comment.unlike(user);
            likeRepository.unlike(comment, user);
        }
    }
}
