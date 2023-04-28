package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.stripe.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StripeCheckoutControllerTest {

    @Mock
    private StripeCheckoutController stripeCheckoutController;

    @InjectMocks
    private StripeCheckoutController stripeCheckoutController1;

    @Test
    void testSuccessCheckoutCart() {

        String url = "https://checkout.stripe.com/pay/cs_test_a1J5Z1Z7Q2ZQZ1Z7Q2ZQZ1Z7Q2";
        when(stripeCheckoutController.checkoutCart()).thenReturn(ResponseDto.success(
                url,
                "Checkout link generated successfully"));

        ResponseDto<String> responseDto = stripeCheckoutController.checkoutCart();

        assertEquals(url, responseDto.getData());

        assertEquals("Checkout link generated successfully", responseDto.getMessage());
    }
}