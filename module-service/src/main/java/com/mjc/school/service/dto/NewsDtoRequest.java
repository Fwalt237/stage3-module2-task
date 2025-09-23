package com.mjc.school.service.dto;

public record NewsDtoRequest(
    Long newsId,
    String title,
    String content,
    Long authorId
) {
}
