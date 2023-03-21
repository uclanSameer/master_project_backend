package com.example.neighbour.dto;

import jakarta.annotation.Nullable;

import java.io.Serializable;

public record EsBusinessDto(
        Integer id,
        String email,
        boolean isFeatured,

        EsLocationRecord location,

        Double rating,

        @Nullable
        UserDetailDto userDetail
) implements Serializable {
}
