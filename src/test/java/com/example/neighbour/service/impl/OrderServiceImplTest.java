package com.example.neighbour.service.impl;

import com.example.neighbour.data.*;
import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.data.cart.CartItem;
import com.example.neighbour.data.cart.CartItemTest;
import com.example.neighbour.data.cart.CartTest;
import com.example.neighbour.enums.PaymentStatus;
import com.example.neighbour.repositories.OrderItemRepository;
import com.example.neighbour.repositories.OrderRepository;
import com.example.neighbour.service.DeliveryService;
import com.example.neighbour.service.aws.S3Service;
import com.example.neighbour.utils.UserUtilsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private S3Service s3Service;

    @Mock
    private DeliveryService deliveryService;


    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void checkoutOrder() {
        Cart cart = CartTest.createCart();

        List<CartItem> cartItems = List.of(
                CartItemTest.createCartItem()
        );

        when(orderRepository.save(any())).thenAnswer(i -> {
            Order order = i.getArgument(0);

            assertEquals(order.getStatus(), PaymentStatus.PAID);
            return order;
        });

        when(orderItemRepository.saveAll(any())).thenAnswer(i -> {
            List<CartItem> cartItems1 = i.getArgument(0);

            assertEquals(cartItems1.size(), 1);
            return cartItems1;
        });

        orderService.checkoutOrder(cartItems, cart);

        verify(orderRepository, times(1)).save(any());

        verify(orderItemRepository, times(1)).saveAll(any());


    }


    @Test
    void getAllOrders() {
        UserUtilsTest.SET_NORMAL_USER_AUTHENTICATION();

        List<MenuItem> menuItems = List.of(
                MenuItemTest.createMenuItem()
        );
        orderService.getOrdersForMenuItems(menuItems);
    }

}