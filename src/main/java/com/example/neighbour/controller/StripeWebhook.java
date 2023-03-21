package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.service.StripeWebhookService;
import com.example.neighbour.utils.ApiConstants;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "webhook")
public class StripeWebhook {

    private final StripeWebhookService stripeWebhookService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseDto<CartTotalDto> webhook(HttpServletRequest request) throws IOException {
        try {
            return stripeWebhookService.handleWebhook(request);
        } catch (StripeException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }
}
