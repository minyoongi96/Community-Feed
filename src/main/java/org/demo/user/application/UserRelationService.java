package org.demo.user.application;

import lombok.RequiredArgsConstructor;
import org.demo.user.application.Interface.UserRelationRepository;
import org.demo.user.application.dto.FollowUserRequestDto;
import org.demo.user.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRelationService {

    private final UserService userService;
    private final UserRelationRepository userRelationRepository;

    public void follow(FollowUserRequestDto dto) {
        User user = userService.getUser(dto.userId());
        User targetUser = userService.getUser(dto.targetUserId());

        if(userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw new IllegalArgumentException();
        }

        user.follow(targetUser);
        userRelationRepository.save(user, targetUser);
    }

    public void unfollow(FollowUserRequestDto dto) {
        User user = userService.getUser(dto.userId());
        User targetUser = userService.getUser(dto.targetUserId());

        if(!userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw new IllegalArgumentException();
        }

        user.unfollow(targetUser);
        userRelationRepository.delete(user, targetUser);
    }
}
