package org.demo.post.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetContentResponseDto {
    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private String userProfileImage;
    private LocalDateTime regDt;
    private LocalDateTime updDt;
    private Integer likeCount;
    private boolean isLikedByMe;
}
