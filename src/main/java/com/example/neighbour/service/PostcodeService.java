package com.example.neighbour.service;

import com.example.neighbour.dto.place.PostCodeData;

public interface PostcodeService {

    /**
     * Gets the post code data from the post code api
     *
     * @param code - post code
     * @return - post code data
     */
    PostCodeData getPostCode(String code);
}
