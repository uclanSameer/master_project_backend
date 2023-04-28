package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.business.BusinessApplicationDto;
import com.example.neighbour.service.BusinessApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessApplicationControllerTest {

    @Mock
    private BusinessApplicationService businessApplicationService;

    @InjectMocks
    private BusinessApplicationController businessApplicationController;

    @Test
    void testCreateApplication() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(businessApplicationService).submitBusinessApplication();

        ResponseDto<String> application = businessApplicationController.createApplication();

        assert (application.getData().equals("Application created successfully"));
    }

    @Test
    void testApproveApplication() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(businessApplicationService).approveBusinessApplication(1);

        ResponseDto<String> application = businessApplicationController.approveApplication(1);

        assert (application.getData().equals("Application approved successfully"));
    }

    @Test
    void testRejectApplication() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(businessApplicationService).rejectBusinessApplication(1);

        ResponseDto<String> application = businessApplicationController.rejectApplication(1);

        assert (application.getData().equals("Application rejected successfully"));
    }

    @Test
    void testGetApplications() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<BusinessApplicationDto> applications = new ArrayList<>();
        applications.add(new BusinessApplicationDto(
                1, 1, "APPROVED", "APPROVED"
        ));
        when(businessApplicationService.getAllBusinessApplications("APPROVED")).thenReturn(
                applications
        );

        ResponseDto<List<BusinessApplicationDto>> status = businessApplicationController.getApplications("APPROVED");

        assert (status.getData().get(0).applicationId() == 1);
    }

}