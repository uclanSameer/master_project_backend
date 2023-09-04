package com.example.neighbour.repositories;

import com.example.neighbour.data.Delivery;
import com.example.neighbour.data.Order;
import com.example.neighbour.data.User;
import com.example.neighbour.enums.PaymentStatus;
import jdk.jshell.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    List<Delivery> findAllByDeliveryPersonNull();

    List<Delivery> findAllByStatus(Delivery.DeliveryStatus status);

    List<Delivery> findAllByDeliveryPersonAndStatus(User deliveryPerson, Delivery.DeliveryStatus status);

    Optional<Delivery> findByOrderId(int orderId);

}