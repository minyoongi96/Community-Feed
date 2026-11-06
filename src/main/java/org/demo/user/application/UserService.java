package org.demo.user.application;

import org.demo.user.application.Interface.UserRepository;
import org.demo.user.application.dto.CreateUserRequestDto;
import org.demo.user.domain.User;
import org.demo.user.domain.UserInfo;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequestDto dto) {
        UserInfo info = new UserInfo(dto.name(), dto.profileImageUrl());
        User user = new User(null, info);

        return userRepository.save(user);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    }
}
