package org.demo.user.ui;

import lombok.RequiredArgsConstructor;
import org.demo.common.ui.Response;
import org.demo.user.application.UserService;
import org.demo.user.application.dto.CreateUserRequestDto;
import org.demo.user.application.dto.GetUserListResponseDto;
import org.demo.user.application.dto.GetUserResponseDto;
import org.demo.user.domain.User;
import org.demo.user.repository.Jpa.JpaUserRepository;
import org.demo.user.repository.Jpa.JpaUserRepositoryCustom;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JpaUserRepository jpaUserRepository;

    @PostMapping
    public Response<Long> createUser(@RequestBody CreateUserRequestDto dto) {
        User user = userService.createUser(dto);
        return Response.ok(user.getId());
    }

    @GetMapping("/{userId}")
    public Response<GetUserResponseDto> getUserProfile(@PathVariable(name = "userId") Long userId) {
        GetUserResponseDto result = userService.getUserProfile(userId);
        return Response.ok(result);
    }

    @GetMapping("/{userId}/followings")
    public Response<List<GetUserListResponseDto>> getFollowingList(@PathVariable(name = "userId") Long userId) {
        List<GetUserListResponseDto> results = jpaUserRepository.getFollowingUserList(userId);
        return Response.ok(results);
    }

    @GetMapping("/{userId}/followers")
    public Response<List<GetUserListResponseDto>> getFollowerList(@PathVariable(name = "userId") Long userId) {
        List<GetUserListResponseDto> results = jpaUserRepository.getFollowerUserList(userId);
        return Response.ok(results);
    }
}
