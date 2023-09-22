package com.example.neighbour.service;

import com.example.neighbour.dto.StripeAccountResponse;
import com.stripe.exception.StripeException;

public interface StripeService {

    /**
     * Creates a session for the current cart then generates checkout url for stripe payment.
     */
    String generateCheckOutUrl() throws StripeException;

    /**
     * This method is used to create a business account on stripe. This returns an url that the user can use to
     * complete the registration process.
     */
    StripeAccountResponse registerBusinessAccount(String email);


    /**
     * This method is used to refresh the account link for the given account id.
     */
    String refreshAccountLink(String accountId);
}
