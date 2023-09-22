package com.example.neighbour.repositories;

import com.example.neighbour.data.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCartIdAndItemId(Integer cartId, Integer itemId);

    void deleteAllByCartId(Integer cartId);

    List<CartItem> findAllByCartId(int cartId);

    void deleteAllCartItemsByCartId(int cartId);
}