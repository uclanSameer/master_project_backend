package com.example.neighbour.repositories;

import com.example.neighbour.data.Order;
import com.example.neighbour.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<List<Order>> findAllByCustomer(User user);
}