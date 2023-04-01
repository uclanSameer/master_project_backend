package com.example.neighbour.dto;

import lombok.Builder;

@Builder
public record MessageDto(
        String toAddress,
        String subject,
        String message
) {
}
