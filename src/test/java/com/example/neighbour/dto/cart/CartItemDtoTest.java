package com.example.neighbour.dto.cart;

import com.example.neighbour.dto.MenuItemDtoTest;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemDtoTest {

    public static CartItemDto getCartItemDto() {
        return new CartItemDto(
                MenuItemDtoTest.getMenuItemDto(),
                1
        );
    }
}