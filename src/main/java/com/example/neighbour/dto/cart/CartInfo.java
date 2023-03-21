package com.example.neighbour.dto.cart;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartInfo {
    public CartTotalDto total;
    public List<CartItemDto> items;

    public CartInfo() {
    }

    public CartInfo(CartTotalDto total, List<CartItemDto> items) {
        this.total = total;
        this.items = items;
    }

}
