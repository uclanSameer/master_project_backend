package com.example.neighbour.service;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface StripeWebhookService {

    /**
     * This webhook is triggered when a checkout.session is completed when the payment is successful.
     * Handles the webhook event from stripe.
     */
    ResponseDto<CartTotalDto> handleWebhook(HttpServletRequest request) throws StripeException, IOException;
}
