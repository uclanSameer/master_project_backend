package com.example.neighbour.data.cart;

import com.example.neighbour.enums.PaymentStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CartTotalTest {
    public static CartTotal createCartTotal() {
        CartTotal cartTotal = new CartTotal();
        cartTotal.setCart(CartTest.createCart());
        cartTotal.setDeliveryCharges(BigDecimal.ZERO);
        cartTotal.setTaxes(BigDecimal.ZERO);
        cartTotal.setTotalCost(BigDecimal.TEN);
        cartTotal.setStatus(PaymentStatus.PAID);
        return cartTotal;
    }
}