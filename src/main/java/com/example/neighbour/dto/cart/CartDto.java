package com.example.neighbour.dto.cart;

import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.dto.UserDto;

import java.io.Serializable;

/**
 * A DTO for the {@link Cart} entity
 */
public record CartDto(
        UserDto user
) implements Serializable {
}