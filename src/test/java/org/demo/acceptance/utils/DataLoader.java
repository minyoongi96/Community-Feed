package org.demo.acceptance.utils;

import org.demo.user.application.dto.CreateUserRequestDto;
import org.demo.user.application.dto.FollowUserRequestDto;
import org.springframework.stereotype.Component;

import static org.demo.acceptance.steps.UserAcceptanceSteps.createUser;
import static org.demo.acceptance.steps.UserAcceptanceSteps.followUser;

@Component
public class DataLoader {

    public void loadData() {
        CreateUserRequestDto dto1 = new CreateUserRequestDto("test_user1", "");
        CreateUserRequestDto dto2 = new CreateUserRequestDto("test_user2", "");
        CreateUserRequestDto dto3 = new CreateUserRequestDto("test_user3", "");
        createUser(dto1);
        createUser(dto2);
        createUser(dto3);

        followUser(new FollowUserRequestDto(1L, 2L));
        followUser(new FollowUserRequestDto(1L, 3L));
    }
}
