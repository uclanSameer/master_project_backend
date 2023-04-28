package com.example.neighbour.dto.cart;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CartTotalDtoTest {

    public static CartTotalDto getCartTotalDto() {
        return new CartTotalDto(
                BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ZERO
        );
    }
}