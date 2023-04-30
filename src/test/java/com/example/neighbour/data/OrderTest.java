package com.example.neighbour.data;

import com.example.neighbour.enums.PaymentStatus;

import java.time.LocalDate;

public class OrderTest {

    public static Order getOrder() {
        Order order = new Order(
                UserTest.getNormalUser(),
                PaymentStatus.UNPAID,
                LocalDate.now()
        );
        order.setId(1);
        return order;
    }
}