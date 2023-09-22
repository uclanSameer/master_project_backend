package com.example.neighbour.dto.delivery;

import com.example.neighbour.data.Delivery;
import com.example.neighbour.data.User;
import com.example.neighbour.dto.users.UserDto;

public record DeliveryResponse(
        int orderNumber,
        String address,
        UserDto deliveryPerson,
        Delivery.DeliveryStatus status
) {

    public DeliveryResponse(Delivery delivery) {
        this(
                delivery.getOrder().getId(),
                delivery.getAddress(),
                GET_USER(delivery.getDeliveryPerson()),
                delivery.getStatus()
        );
    }

    private static UserDto GET_USER(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user);
    }
}
