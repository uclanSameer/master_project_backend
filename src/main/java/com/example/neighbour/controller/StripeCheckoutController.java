package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.service.StripeService;
import com.example.neighbour.utils.ApiConstants;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "payment")
public class StripeCheckoutController {

    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseDto<String> checkoutCart() {
        try {
            log.info("Checking out cart ");
            String checkoutCart = stripeService.generateCheckOutUrl();
            return ResponseDto.success(checkoutCart, "Checkout link generated successfully");
        } catch (StripeException e) {
            log.error("Error while checking out cart: {}", e.getMessage());
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Error while checking out cart");
        }
    }
}
