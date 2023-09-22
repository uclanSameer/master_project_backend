package com.example.neighbour.dto.business;

import com.example.neighbour.data.BusinessApplication;

public record BusinessApplicationDto(
        int applicationId,
        int userId,
        String status,
        String remarks
) {

    public BusinessApplicationDto(BusinessApplication application) {
        this(
                application.getId(),
                application.getUser().getId(),
                application.getStatus(),
                application.getRemarks()
        );
    }
}
