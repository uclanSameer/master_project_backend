package com.example.neighbour.dto.cart;

import com.example.neighbour.data.cart.CartItem;
import com.example.neighbour.dto.MenuItemDto;

import java.io.Serializable;

/**
 * A DTO for the {@link CartItem} entity
 */
public record CartItemDto(
        MenuItemDto item,
        Integer quantity
) implements Serializable {
}