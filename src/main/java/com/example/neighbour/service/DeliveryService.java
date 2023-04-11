package com.example.neighbour.service;

import com.example.neighbour.data.Order;
import com.example.neighbour.dto.order.OrderItemResponse;

import java.util.List;

public interface DeliveryService {


    void createDelivery(Order order, String formattedAddress);
}
