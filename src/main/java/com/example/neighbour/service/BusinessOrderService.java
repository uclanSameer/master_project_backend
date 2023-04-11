package com.example.neighbour.service;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.order.OrderItemResponse;

import java.util.List;

public interface BusinessOrderService {

    ResponseDto<List<OrderItemResponse>> getOrdersForBusiness();
}
