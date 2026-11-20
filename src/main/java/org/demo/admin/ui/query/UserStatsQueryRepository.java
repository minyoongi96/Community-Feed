package org.demo.admin.ui.query;

import org.demo.admin.ui.dto.GetDailyRegisterResponseDto;

import java.util.List;

public interface UserStatsQueryRepository {
    List<GetDailyRegisterResponseDto> getDailyRegisterUserStatList(int beforeDays);
}
