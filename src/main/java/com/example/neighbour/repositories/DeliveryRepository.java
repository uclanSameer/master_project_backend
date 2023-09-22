package com.example.neighbour.repositories;

import com.example.neighbour.data.Delivery;
import com.example.neighbour.data.Order;
import com.example.neighbour.data.User;
import com.example.neighbour.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    List<Delivery> findAllByDeliveryPersonNull();

    List<Delivery> findAllByStatus(Delivery.DeliveryStatus status);

    List<Delivery> findAllByDeliveryPerson(User deliveryPerson);

    Optional<Delivery> findByOrderId(int orderId);

}