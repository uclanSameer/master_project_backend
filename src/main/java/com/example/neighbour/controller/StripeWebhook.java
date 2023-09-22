package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.service.StripeService;
import com.example.neighbour.service.StripeWebhookService;
import com.example.neighbour.utils.ApiConstants;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "webhook")
@Slf4j
public class StripeWebhook {

    private final StripeWebhookService stripeWebhookService;
    private final StripeService stripeService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseDto<CartTotalDto> webhook(HttpServletRequest request) throws IOException {
        try {
            return stripeWebhookService.handleWebhook(request);
        } catch (StripeException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }

    @GetMapping("/account/refresh/{accountId}")
    public ModelAndView refreshAccountLink(@PathVariable String accountId) {
        log.info("Refreshing account link for account: {}", accountId);
        String accountLink = stripeService.refreshAccountLink(accountId);
        return new ModelAndView("redirect:" + accountLink);
    }
}
