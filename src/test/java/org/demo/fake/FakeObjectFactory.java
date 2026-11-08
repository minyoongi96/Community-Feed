package org.demo.fake;

import org.demo.post.application.CommentSerivce;
import org.demo.post.application.Interface.CommentRepository;
import org.demo.post.application.Interface.LikeRepository;
import org.demo.post.application.Interface.PostRepository;
import org.demo.post.application.PostService;
import org.demo.post.repository.FakeCommentRepository;
import org.demo.post.repository.FakeLikeRepository;
import org.demo.post.repository.FakePostRepository;
import org.demo.user.application.Interface.UserRelationRepository;
import org.demo.user.application.Interface.UserRepository;
import org.demo.user.application.UserRelationService;
import org.demo.user.application.UserService;
import org.demo.user.repository.FakeUserRelationRepository;
import org.demo.user.repository.FakeUserRepository;

public class FakeObjectFactory {

    private static final UserRepository fakeUserRepository = new FakeUserRepository();
    private static final UserRelationRepository fakeUserRelationRepository = new FakeUserRelationRepository();
    private static final PostRepository fakePostRepository = new FakePostRepository();
    private static final CommentRepository fakeCommentRepository = new FakeCommentRepository();
    private static final LikeRepository fakeLikeRepository = new FakeLikeRepository();

    private static final UserService userService = new UserService(fakeUserRepository);
    private static final UserRelationService userRelationService = new UserRelationService(userService, fakeUserRelationRepository);
    private static final PostService postService = new PostService(fakePostRepository, fakeLikeRepository, userService);
    private static final CommentSerivce commentSerivce = new CommentSerivce(fakeCommentRepository, userService, postService, fakeLikeRepository);

    private FakeObjectFactory() {

    }

    public static UserService getUserService() {
        return userService;
    }
    public static UserRelationService getUserRelationService() {
        return userRelationService;
    }
    public static PostService getPostService() {
        return postService;
    }
    public static CommentSerivce getCommentSerivce() {
        return commentSerivce;
    }
}
