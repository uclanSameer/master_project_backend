package com.example.neighbour.data;

public class DeliveryTest {

    public static Delivery getDelivery() {
        return new Delivery(
                1,
                OrderTest.getOrder(),
                "123 Fake Street",
                UserTest.getNormalUser(),
                Delivery.DeliveryStatus.PENDING
        );
    }
}