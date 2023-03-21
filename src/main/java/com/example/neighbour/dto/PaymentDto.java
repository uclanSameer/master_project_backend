package com.example.neighbour.dto;

import com.example.neighbour.dto.order.OrderDto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.neighbour.data.Payment} entity
 */
public record PaymentDto(OrderDto order, String method, String status, String transactionId) implements Serializable {
}