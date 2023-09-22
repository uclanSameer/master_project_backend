package com.example.neighbour.dto.delivery;

import com.example.neighbour.data.Delivery;
import com.example.neighbour.dto.users.UserDto;

public record DeliveryRequest(
        int orderNumber,
        String emailOfDeliveryPerson
) {
}
