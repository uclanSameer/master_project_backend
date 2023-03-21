package com.example.neighbour.repositories;

import com.example.neighbour.data.Order;
import com.example.neighbour.data.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    Optional<List<OrderItem>> findAllByOrder(Order order);

}