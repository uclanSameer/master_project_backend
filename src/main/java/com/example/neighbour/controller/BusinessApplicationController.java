package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.BusinessApplicationDto;
import com.example.neighbour.service.BusinessApplicationService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "applications")
public class BusinessApplicationController {

    private final BusinessApplicationService businessApplicationService;

    @PostMapping("/create")
    public ResponseDto<String> createApplication() {
        log.info("Creating application");
        businessApplicationService.submitBusinessApplication();
        return new ResponseDto<>("Application created successfully");
    }

    @PostMapping("/approve/{applicationId}")
    public ResponseDto<String> approveApplication(@PathVariable int applicationId) {
        log.info("Approving application: {}", applicationId);
        businessApplicationService.approveBusinessApplication(applicationId);
        return new ResponseDto<>("Application approved successfully");
    }

    @PostMapping("/reject/{applicationId}")
    public ResponseDto<String> rejectApplication(@PathVariable int applicationId) {
        log.info("Rejecting application");
        businessApplicationService.rejectBusinessApplication(applicationId);
        return new ResponseDto<>("Application rejected successfully");
    }

    @GetMapping("/")
    public ResponseDto<List<BusinessApplicationDto>> getApplications(@Param("status") String status) {
        log.info("Getting applications");
        List<BusinessApplicationDto> applicationDtos = businessApplicationService
                .getAllBusinessApplications(status);
        return new ResponseDto<>(applicationDtos);

    }
}
