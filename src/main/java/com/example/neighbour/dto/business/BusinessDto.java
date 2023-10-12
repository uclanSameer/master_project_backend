package com.example.neighbour.dto.business;

import com.example.neighbour.data.Business;
import com.example.neighbour.dto.place.EsLocationRecord;
import com.example.neighbour.dto.users.UserDetailDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

import java.io.Serializable;

/**
 * A DTO for the {@link Business} entity
 */
public record BusinessDto(
        Long id,
        String email,
        boolean isFeatured,

        EsLocationRecord location,

        Double rating,

        @Nullable
        UserDetailDto userDetail

) implements Serializable {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public BusinessDto(
            @JsonProperty("email") String email,
            @JsonProperty("isFeatured") boolean isFeatured,
            @JsonProperty("location") EsLocationRecord location,
            @JsonProperty("rating") Double rating,
            @JsonProperty("userDetail") @Nullable UserDetailDto userDetail
    ) {
        this(null, email, isFeatured, location, rating, userDetail);
    }

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

