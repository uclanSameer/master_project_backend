package com.example.neighbour.dto;

import com.example.neighbour.data.Business;
import jakarta.annotation.Nullable;

import java.io.Serializable;

/**
 * A DTO for the {@link Business} entity
 */
public record BusinessDto(
        String email,
        boolean isFeatured,

        EsLocationRecord location,

        Double rating,

        @Nullable
        UserDetailDto userDetail

) implements Serializable {
}

