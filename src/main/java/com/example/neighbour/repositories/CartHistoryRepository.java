package com.example.neighbour.repositories;

import com.example.neighbour.data.cart.CartHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartHistoryRepository extends JpaRepository<CartHistory, Long> {
}