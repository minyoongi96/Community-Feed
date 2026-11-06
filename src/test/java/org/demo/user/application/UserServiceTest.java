package org.demo.user.application;

import org.demo.user.application.Interface.UserRepository;
import org.demo.user.application.dto.CreateUserRequestDto;
import org.demo.user.domain.User;
import org.demo.user.repository.FakeUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    private final UserRepository userRepository = new FakeUserRepository();
    private final UserService userService = new UserService(userRepository);

    @Test
    void givenUserInfoDto_whenCreateUser_thenCanFindUser(){
        // given
        CreateUserRequestDto dto = new CreateUserRequestDto("test", "");

        // when
        User savedUser = userService.createUser(dto);

        // then
        User foundUser = userService.getUser(savedUser.getId());
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals("test", foundUser.getInfo().getName());

    }
}
