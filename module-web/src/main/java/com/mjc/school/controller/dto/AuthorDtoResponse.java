package com.mjc.school.controller.dto;

import java.time.LocalDateTime;

public record AuthorDtoResponse(
    Long authorId,
    String name,
    LocalDateTime createDate,
    LocalDateTime lastUpdatedDate
) {
}
