package com.mjc.school.controller.dto;

public record NewsDtoRequest(
    Long newsId,
    String title,
    String content,
    Long authorId
) {
}
