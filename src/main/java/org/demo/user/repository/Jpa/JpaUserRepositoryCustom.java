package org.demo.user.repository.Jpa;

import org.demo.user.application.dto.GetUserListResponseDto;

import java.util.List;

public interface JpaUserRepositoryCustom {

    List<GetUserListResponseDto> getFollowingUserList(Long userId);

    List<GetUserListResponseDto> getFollowerUserList(Long userId);

    List<GetUserListResponseDto> getFollowingUserListWithPaging(Long userId, Long lastFollowingId);

    List<GetUserListResponseDto> getFollowerUserListWithPaging(Long userId, Long lastFollowerId);
}
