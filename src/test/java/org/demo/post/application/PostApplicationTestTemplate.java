package org.demo.post.application;

import org.demo.fake.FakeObjectFactory;
import org.demo.post.application.dto.CreateCommentRequestDto;
import org.demo.post.application.dto.CreatePostRequestDto;
import org.demo.post.domain.Post;
import org.demo.post.domain.content.PostPublicationState;
import org.demo.user.application.UserService;
import org.demo.user.application.dto.CreateUserRequestDto;
import org.demo.user.domain.User;

public class PostApplicationTestTemplate {
    final UserService userService = FakeObjectFactory.getUserService();
    final PostService postService = FakeObjectFactory.getPostService();
    final CommentSerivce commentService = FakeObjectFactory.getCommentSerivce();

    final User user = userService.createUser(new CreateUserRequestDto("user1", ""));
    final User otherUser = userService.createUser(new CreateUserRequestDto("user2", ""));

    CreatePostRequestDto postRequestDto = new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC);
    final Post post = postService.createPost(postRequestDto);

    String commentConentText = "comment content";
    CreateCommentRequestDto commentRequestDto = new CreateCommentRequestDto(post.getId(), user.getId(), commentConentText);
}
가