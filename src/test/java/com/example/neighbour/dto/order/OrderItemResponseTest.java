package com.example.neighbour.dto.order;

import com.example.neighbour.dto.MenuItemDtoTest;

import java.math.BigDecimal;

public class OrderItemResponseTest {

    public static OrderItemResponse createOrderItemResponse() {
        return new OrderItemResponse(
                MenuItemDtoTest.getMenuItemDto(),
                1,
                BigDecimal.TEN);
    }
}