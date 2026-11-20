package org.demo.admin.ui.query;

import org.demo.admin.ui.dto.users.GetDailyRegisterResponseDto;

import java.util.List;

public interface UserStatsQueryRepository {
    List<GetDailyRegisterResponseDto> getDailyRegisterUserStatList(int beforeDays);
}
