package com.example.neighbour.data;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    public static OrderItem createOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setItem(MenuItemTest.createMenuItem());
        orderItem.setOrder(OrderTest.getOrder());
        orderItem.setQuantity(1);
        orderItem.setPrice(MenuItemTest.createMenuItem().getPrice());
        orderItem.setNutritionalInfo(MenuItemTest.createMenuItem().getNutritionalInfo());
        return orderItem;
    }
}