package org.demo.user.application;

import org.demo.user.application.Interface.UserRelationRepository;
import org.demo.user.application.Interface.UserRepository;
import org.demo.user.application.dto.CreateUserRequestDto;
import org.demo.user.application.dto.FollowUserRequestDto;
import org.demo.user.domain.User;
import org.demo.user.repository.FakeUserRelationRepository;
import org.demo.user.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserRelationServiceTest {

    private final UserRepository userRepository = new FakeUserRepository();
    private final UserRelationRepository userRelationRepository = new FakeUserRelationRepository();
    private final UserService userService = new UserService(userRepository);
    private final UserRelationService userRelationService = new UserRelationService(userService, userRelationRepository);

    private User user1;
    private User user2;
    private FollowUserRequestDto requestDto;

    @BeforeEach
    void init(){
        CreateUserRequestDto dto1 = new CreateUserRequestDto("test1", "");
        CreateUserRequestDto dto2 = new CreateUserRequestDto("test2", "");

        this.user1 = userService.createUser(dto1);
        this.user2 = userService.createUser(dto2);

        this.requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());
    }

    @Test
    void givenCreateTwoUser_whenFollow_thenUserFollowSaved(){
        // when
        userRelationService.follow(requestDto);

        // then
        assertEquals(1, user1.getFollowingCount());
        assertEquals(1, user2.getFollowerCount());
    }

    @Test
    void givenCreateTwoUserFollowed_whenFollow_thenUserThrowError(){
        // given
        userRelationService.follow(requestDto);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.follow(requestDto));
    }

    @Test
    void givenOneUser_whenFollowSelf_thenUserThrowError(){
        // given
        FollowUserRequestDto dto = new FollowUserRequestDto(user1.getId(), user1.getId());

        // when
        assertThrows(IllegalArgumentException.class, () -> userRelationService.follow(dto));
    }

    @Test
    void givenCreateTwoUserFollowed_whenUnfollow_thenUserFollowSaved(){
        // given
        userRelationService.follow(requestDto);

        // when
        userRelationService.unfollow(requestDto);

        // then
        assertEquals(0, user1.getFollowingCount());
        assertEquals(0, user2.getFollowerCount());
    }

    @Test
    void givenCreateTwoUser_whenUnfollow_thenUserThrowError(){
        // when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.unfollow(requestDto));
    }

    @Test
    void givenOneUser_whenUnfollowSelf_thenUserThrowError(){
        // given
        FollowUserRequestDto dto = new FollowUserRequestDto(user1.getId(), user1.getId());

        // when
        assertThrows(IllegalArgumentException.class, () -> userRelationService.unfollow(dto));
    }
}
