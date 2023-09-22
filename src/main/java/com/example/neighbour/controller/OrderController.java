package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.order.OrderItemResponse;
import com.example.neighbour.service.BusinessOrderService;
import com.example.neighbour.service.OrderService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "order")
public class OrderController {

    private final OrderService orderService;

    private final BusinessOrderService businessOrderService;

    @PostMapping("/all")
    public ResponseDto<List<OrderItemResponse>> getAllOrders() {
        log.info("Getting all orders");
        return orderService.getAllOrders();
    }

    @PostMapping("/business/all")
    public ResponseDto<List<OrderItemResponse>> getAllOrdersForBusiness() {
        log.info("Getting all orders for business");
        return businessOrderService.getOrdersForBusiness();
    }
}
