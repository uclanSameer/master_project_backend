package com.example.neighbour.data.cart;

import com.example.neighbour.data.MenuItemTest;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemTest {

    public static CartItem createCartItem() {
        CartItem cartItem = new CartItem();
        cartItem.setId(1);
        cartItem.setQuantity(1);
        cartItem.setCart(CartTest.createCart());
        cartItem.setItem(MenuItemTest.createMenuItem());
        return cartItem;
    }

}