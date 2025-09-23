package com.mjc.school.controller.dto;

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
