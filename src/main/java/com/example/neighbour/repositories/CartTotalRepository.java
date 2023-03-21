package com.example.neighbour.repositories;

import com.example.neighbour.data.cart.CartTotal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartTotalRepository extends JpaRepository<CartTotal, Long> {

    void deleteByCartId(int cartId);

    Optional<CartTotal> findByCartId(int cartId);
}