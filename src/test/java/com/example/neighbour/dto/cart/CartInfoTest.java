package com.example.neighbour.dto.cart;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartInfoTest {
        public static CartInfo getCartInfo() {
                return new CartInfo(
                        CartTotalDtoTest.getCartTotalDto(),
                        List.of(CartItemDtoTest.getCartItemDto())
                );
        }
}