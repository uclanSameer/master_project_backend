package com.example.neighbour.dto;

import lombok.Builder;

@Builder
public record MessageDto(
        String to,
        String subject,
        String message,

        boolean isHtml
) {
}
