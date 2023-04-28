package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.dto.cart.CartTotalDtoTest;
import com.example.neighbour.service.StripeService;
import com.example.neighbour.service.StripeWebhookService;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StripeWebhookTest {

    @Mock private StripeWebhookService stripeWebhookService;
    @Mock private StripeService stripeService;

    @InjectMocks
    private StripeWebhook stripeWebhook;

    @Test
    void testSuccessWebhook() throws StripeException, IOException {
        CartTotalDto cartTotalDto = CartTotalDtoTest.getCartTotalDto();
        when(stripeWebhookService.handleWebhook(any())).thenReturn(
                ResponseDto.success(
                        cartTotalDto,
                        "Webhook handled successfully"));

        MockHttpServletRequest request = new MockHttpServletRequest();

        ResponseDto<CartTotalDto> responseDto = stripeWebhook.webhook(request);

        assertEquals(cartTotalDto, responseDto.getData());
    }

    @Test
    void testGeneratingRefreshAccountLink(){
        String url = "https://connect.stripe.com/setup/c/xxxxxxxxxxxxxxxxxxxxxxxx";
        when(stripeService.refreshAccountLink(any())).thenReturn(
                url);

        ModelAndView responseDto = stripeWebhook.refreshAccountLink("xxxxxxxxxxxxxxxxxxxxxxxx");

        var viewName = "redirect:"+ url;
        assertEquals(viewName, responseDto.getViewName());


    }

}