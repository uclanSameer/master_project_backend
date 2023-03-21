package com.example.neighbour.service.impl;

import com.example.neighbour.configuration.security.permissions.ROLE_ADMIN;
import com.example.neighbour.data.BusinessApplication;
import com.example.neighbour.data.User;
import com.example.neighbour.dto.*;
import com.example.neighbour.enums.Role;
import com.example.neighbour.repositories.BusinessApplicationRepository;
import com.example.neighbour.service.BusinessApplicationService;
import com.example.neighbour.service.BusinessService;
import com.example.neighbour.service.PostcodeService;
import com.example.neighbour.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class BusinessApplicationServiceImpl implements BusinessApplicationService {

    private final BusinessApplicationRepository repository;

    private final BusinessService businessService;

    private final PostcodeService postcodeService;

    @Override
    public void submitBusinessApplication() {
        User user = UserUtils.getAuthenticatedUser();

        if (Role.BUSINESS.equals(user.getRole()) || Role.ADMIN.equals(user.getRole())) {
            log.error("User is already a Business or admin");
            throw new ResponseStatusException(BAD_REQUEST, "You are already a Business user");
        }
        repository.findByUserId(user.getId())
                .ifPresent(application -> {
                    log.error("{} has already submitted an application", user.getEmail());
                    throw new ResponseStatusException(BAD_REQUEST, "You have already submitted an application");
                });
        BusinessApplication application = new BusinessApplication();
        application.setUser(user);
        application.setStatus("PENDING");
        application.setRemarks("");
        repository.save(application);
        log.info("Application submitted successfully");
    }

    @ROLE_ADMIN
    @Transactional
    @Override
    public void approveBusinessApplication(int applicationId) {
        BusinessApplication application = repository.findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Invalid application id"));

        User user = application.getUser();
        PostCodeData postCode = postcodeService.getPostCode(user.getUserDetail().getAddress().getPostalCode());

        EsLocationRecord location = new EsLocationRecord(
                postCode.latitude(),
                postCode.longitude()
        );

        BusinessDto businessDto = new BusinessDto(
                user.getEmail(),
                false,
                location,
                0D,
                new UserDetailDto(user.getUserDetail())
        );
//        businessService.addBusiness(businessDto);
        application.setStatus("APPROVED");
        repository.save(application);


    }

    @ROLE_ADMIN
    @Override
    public void rejectBusinessApplication(int applicationId) {
        BusinessApplication application = repository.findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Invalid application id"));
        application.setStatus("REJECTED");
        repository.save(application);
    }

    @ROLE_ADMIN
    @Override
    public List<BusinessApplicationDto> getAllBusinessApplications(String status) {
        return repository.findAllByStatus(status)
                .stream()
                .map(BusinessApplicationDto::new)
                .toList();
    }
}
