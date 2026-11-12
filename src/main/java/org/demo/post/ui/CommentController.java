package org.demo.post.ui;

import lombok.RequiredArgsConstructor;
import org.demo.common.ui.Response;
import org.demo.post.application.CommentService;
import org.demo.post.application.dto.CreateCommentRequestDto;
import org.demo.post.application.dto.LikeRequestDto;
import org.demo.post.application.dto.UpdateCommentRequestDto;
import org.demo.post.domain.comment.Comment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Response<Long> createComment(@RequestBody CreateCommentRequestDto dto) {
        Comment comment = commentService.createComment(dto);
        return Response.ok(comment.getId());
    }

    @PatchMapping("/{commentId}")
    public Response<Long> updateComment(@PathVariable(name = "commentId") Long commentId, @RequestBody UpdateCommentRequestDto dto) {
        Comment comment = commentService.updateComment(commentId, dto);
        return Response.ok(comment.getId());
    }

    @PostMapping("/like")
    public Response<Void> likeComment(@RequestBody LikeRequestDto dto){
        commentService.likeComment(dto);
        return Response.ok(null);
    }

    @PostMapping("/unlike")
    public Response<Void> unlikeComment(@RequestBody LikeRequestDto dto){
        commentService.unlikeComment(dto);
        return Response.ok(null);
    }
}
