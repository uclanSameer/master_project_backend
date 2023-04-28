package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.order.OrderItemResponse;
import com.example.neighbour.dto.order.OrderItemResponseTest;
import com.example.neighbour.service.BusinessOrderService;
import com.example.neighbour.service.OrderService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private BusinessOrderService businessOrderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testGetAllOrders() {
        when(orderService.getAllOrders()).thenReturn(
                ResponseDto.success(
                        List.of(OrderItemResponseTest.createOrderItemResponse()),
                        "Success"
                )
        );
        ResponseDto<List<OrderItemResponse>> responseDto = orderController.getAllOrders();

        assertEquals(1, responseDto.getData().size());
        assertEquals("Success", responseDto.getMessage());
    }

    @Test
    void testGetAllOrdersForBusiness() {
        when(businessOrderService.getOrdersForBusiness()).thenReturn(
                ResponseDto.success(
                        List.of(OrderItemResponseTest.createOrderItemResponse()),
                        "Success"
                )
        );
        ResponseDto<List<OrderItemResponse>> responseDto = orderController.getAllOrdersForBusiness();

        assertEquals(1, responseDto.getData().size());
        assertEquals("Success", responseDto.getMessage());
    }
}