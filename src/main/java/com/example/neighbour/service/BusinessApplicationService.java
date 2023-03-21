package com.example.neighbour.service;

import com.example.neighbour.dto.BusinessApplicationDto;

import java.util.List;

public interface BusinessApplicationService {

    /**
     * Used to submit by a non-business user to request to become a business
     */
    void submitBusinessApplication();

    /**
     * Used to approve a business application only by admin
     */
    void approveBusinessApplication(int applicationId);

    /**
     * Used to reject a business application only by admin
     */
    void rejectBusinessApplication(int applicationId);

    /**
     * Used to get view all business applications requested by non-business users
     */
    List<BusinessApplicationDto> getAllBusinessApplications(String status);
}
