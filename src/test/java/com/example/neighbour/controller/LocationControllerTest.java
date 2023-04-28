package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.place.LocationResponse;
import com.example.neighbour.dto.place.LocationResponseTest;
import com.example.neighbour.service.AddressFinderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    private AddressFinderService addressFinderService;

    @InjectMocks
    private LocationController locationController;

    @Test
    void testGetListOfAddress() {

        String postCode = "PR1 7AA";
        var listOfAddress = List.of(LocationResponseTest.getLocationResponse());
        when(addressFinderService.getListOfAddress(postCode)).thenReturn(
                ResponseDto.success(listOfAddress, "Address list retrieved successfully")
        );

        ResponseDto<List<LocationResponse>> responseDto = locationController.getListOfAddress(postCode);

        assert (responseDto.getData().equals(listOfAddress));
    }

}