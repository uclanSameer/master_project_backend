package com.example.neighbour.service.impl;

import com.example.neighbour.data.Delivery;
import com.example.neighbour.data.Order;
import com.example.neighbour.exception.ErrorResponseException;
import com.example.neighbour.repositories.DeliveryRepository;
import com.example.neighbour.service.DeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public void createDelivery(Order order, String formattedAddress) {
        try {
            var delivery = new Delivery();
            delivery.setOrder(order);
            delivery.setStatus(Delivery.DeliveryStatus.PENDING);
            delivery.setDeliveryPerson("Not Assigned");
            delivery.setAddress(formattedAddress);

            deliveryRepository.save(delivery);
            log.info("Delivery created for order: {}", order.getId());
        } catch (Exception e) {
            log.error("Error creating delivery for order: {}", order.getId(), e);
            throw new ErrorResponseException(500, "Error creating delivery");
        }
    }
}
