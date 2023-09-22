package com.example.neighbour.dto;

import com.example.neighbour.dto.order.OrderDto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.neighbour.data.Delivery} entity
 */
public record DeliveryDto(OrderDto order, String address, String deliveryPerson,
                          String status) implements Serializable {
}