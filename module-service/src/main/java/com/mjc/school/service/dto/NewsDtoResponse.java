package com.mjc.school.service.dto;

import java.time.LocalDateTime;

public record NewsDtoResponse(
    Long newsId,
    String title,
    String content,
    LocalDateTime createDate,
    LocalDateTime lastUpdatedDate,
    Long authorId
) {
}
