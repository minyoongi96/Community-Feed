package org.demo.admin.ui.dto.posts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.demo.common.utils.TimeCalculator;

import java.time.LocalDateTime;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostTableResponseDto {
    @Getter
    private Long postId;
    @Getter
    private Long userId;
    @Getter
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 글자수 제한해서 짤라 보여주기
    public String getContent() {
        if(content.length() > 10) {
            return content.substring(0, 10) + "...";
        }
        return content;
    }

    public String getCreatedAt() {
        return TimeCalculator.getFormattedDate(createdAt);
    }

    public String getUpdatedAt() {
        return TimeCalculator.getFormattedDate(updatedAt);
    }
}
