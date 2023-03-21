package com.example.neighbour.service;

import com.example.neighbour.dto.LocationResponse;
import com.example.neighbour.dto.ResponseDto;

import java.util.List;

public interface AddressFinderService {

    /**
     * Get list of address for a given post code
     *
     * @param postCode post code
     * @return list of address
     */
    ResponseDto<List<LocationResponse>> getListOfAddress(String postCode);
}
