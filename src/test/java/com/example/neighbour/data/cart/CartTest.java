package com.example.neighbour.data.cart;

import com.example.neighbour.data.UserTest;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    public static Cart createCart() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setUser(UserTest.getNormalUser());
        return cart;
    }

}