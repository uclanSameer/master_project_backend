package com.example.neighbour.dto.cart;

import com.example.neighbour.data.cart.CartTotal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link CartTotal} entity
 */
public record CartTotalDto(
        BigDecimal totalCost,
        BigDecimal taxes,
        BigDecimal deliveryCharges) implements Serializable {
}