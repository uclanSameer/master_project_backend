package com.example.neighbour.dto.business;

import com.example.neighbour.data.Business;
import com.example.neighbour.dto.place.EsLocationRecord;
import com.example.neighbour.dto.users.UserDetailDto;
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

    public BusinessDto(Business business) {
        this(
                business.getUser().getEmail(),
                business.isFeatured(),
                null,
                null,
                null
        );
    }
}

