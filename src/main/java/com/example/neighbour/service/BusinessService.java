package com.example.neighbour.service;

import com.example.neighbour.data.Business;

public interface BusinessService {
    /**
     * Adds business to the database and returns a url to complete the registration via stripe
     *
     * @param business - business to be added
     * @return - response dto
     */
    String addBusiness(Business business);

    /**
     * Gets the current business
     *
     * @return - business
     */
    Business getCurrentBusiness();

    /**
     * Gets business by account id
     *
     * @param accountId - account id
     * @return - business
     */
    Business getBusinessByAccountId(String accountId);

}
