package com.mjc.school.service.dto;

import java.time.LocalDateTime;

public record AuthorDtoRequest(
    Long authorId,
    String name
) {
}
