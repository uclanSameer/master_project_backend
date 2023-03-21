package com.example.neighbour.dto.order;

import java.io.Serializable;

/**
 *
 */
public record OrderItemDto(
        int menuId,
        int quantity) implements Serializable {

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "menuId=" + menuId +
                ", quantity=" + quantity +
                '}';
    }
}