package org.demo.post.ui;

import lombok.RequiredArgsConstructor;
import org.demo.common.principal.AuthPrincipal;
import org.demo.common.principal.UserPrincipal;
import org.demo.common.ui.Response;
import org.demo.post.repository.post_queue.UserPostQueueQueryRepository;
import org.demo.post.ui.dto.GetPostContentResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final UserPostQueueQueryRepository queueQueryRepository;

    @GetMapping("")
    public Response<List<GetPostContentResponseDto>> getPostFeed(@AuthPrincipal UserPrincipal userPrincipal, Long lastPostId) {
        List<GetPostContentResponseDto> result = queueQueryRepository.getPostContentList(userPrincipal.getUserId(), lastPostId);
        return Response.ok(result);
    }
}
