package com.example.neighbour.controller;

import com.example.neighbour.dto.business.BusinessDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.service.BusinessService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(ApiConstants.API_VERSION_1 + "business")
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping("/add")
    public ResponseDto<String> addBusiness(@RequestBody BusinessDto businessDto) {
        log.info("Adding seller: {}", businessDto);
//        return businessService.addBusiness(businessDto);
//        TODO: add business
        return null;
    }

    @PostMapping("/register")
    public ResponseDto<String> registerBusiness(@RequestBody UserDto userDto) {
        log.info("Registering seller: {}", userDto);
//        return businessService.registerBusiness(userDto);
//        TODO: register business
        return null;
    }
}
