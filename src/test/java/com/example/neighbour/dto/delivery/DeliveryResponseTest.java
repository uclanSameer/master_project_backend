package com.example.neighbour.dto.delivery;

import com.example.neighbour.data.Delivery;
import com.example.neighbour.dto.users.UserDtoTest;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryResponseTest {

    public static DeliveryResponse getDeliveryResponse() {
        return new DeliveryResponse(
                1,
                "test",
                UserDtoTest.getUserDto(),
                Delivery.DeliveryStatus.DELIVERED
        );
    }

}