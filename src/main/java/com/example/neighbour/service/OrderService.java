package com.example.neighbour.service;

import com.example.neighbour.data.MenuItem;
import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.data.cart.CartItem;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.order.OrderItemResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface OrderService {

    /**
     * Place order for the cart
     */
    void checkoutOrder(@NotNull List<CartItem> cartItems, Cart cart);

    /**
     * Get all orders for a current user
     */
    ResponseDto<List<OrderItemResponse>> getAllOrders();


    ResponseDto<List<OrderItemResponse>> getOrdersForMenuItems(List<MenuItem> menuItems);

}
