package org.demo.user.repository.Jpa;

import org.demo.user.application.dto.GetUserListResponseDto;

import java.util.List;

public interface JpaUserRepositoryCustom {

    List<GetUserListResponseDto> getFollowingUserList(Long userId, Long lastFollowingId);

    List<GetUserListResponseDto> getFollowerUserList(Long userId, Long lastFollowerId);
}
