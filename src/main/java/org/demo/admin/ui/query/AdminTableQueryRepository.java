package org.demo.admin.ui.query;

import org.demo.admin.ui.dto.GetTableListResponse;
import org.demo.admin.ui.dto.posts.GetPostTableRequestDto;
import org.demo.admin.ui.dto.posts.GetPostTableResponseDto;
import org.demo.admin.ui.dto.users.GetUserTableRequestDto;
import org.demo.admin.ui.dto.users.GetUserTableResponseDto;

public interface AdminTableQueryRepository {

    GetTableListResponse<GetUserTableResponseDto> getUserTableData(GetUserTableRequestDto dto);
    GetTableListResponse<GetPostTableResponseDto> getPostTableData(GetPostTableRequestDto dto);
}
