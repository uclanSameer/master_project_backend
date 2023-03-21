package com.example.neighbour.dto.cart;

import com.example.neighbour.data.cart.CartHistory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A DTO for the {@link CartHistory} entity
 */
public record CartHistoryDto(
        CartDto cart,
        String itemsAdded,
        String itemsRemoved,
        Instant changesTime,
        BigDecimal totalCost) implements Serializable {
}