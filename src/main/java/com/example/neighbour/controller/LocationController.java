package com.example.neighbour.controller;


import com.example.neighbour.dto.place.LocationResponse;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.service.AddressFinderService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "address")
public class LocationController {

    private final AddressFinderService addressFinderService;

    @GetMapping()
    public ResponseDto<List<LocationResponse>> getListOfAddress(
            @Param("postCode") String postCode) {
        {
            log.info("Getting all addresses for post code: {}", postCode);
            return addressFinderService.getListOfAddress(postCode);
        }
    }
}
