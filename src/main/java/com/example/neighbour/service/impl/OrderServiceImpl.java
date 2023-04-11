package com.example.neighbour.service.impl;

import com.example.neighbour.configuration.security.permissions.ROLE_BUSINESS;
import com.example.neighbour.data.MenuItem;
import com.example.neighbour.data.Order;
import com.example.neighbour.data.OrderItem;
import com.example.neighbour.data.User;
import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.data.cart.CartItem;
import com.example.neighbour.dto.MenuItemDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.order.OrderItemResponse;
import com.example.neighbour.enums.PaymentStatus;
import com.example.neighbour.repositories.OrderItemRepository;
import com.example.neighbour.repositories.OrderRepository;
import com.example.neighbour.service.DeliveryService;
import com.example.neighbour.service.MenuService;
import com.example.neighbour.service.OrderService;
import com.example.neighbour.service.aws.S3Service;
import com.example.neighbour.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final S3Service s3Service;

    private final DeliveryService deliveryService;

    /**
     * Convert cart items to order items
     */
    private static List<OrderItem> convertCartItemsToOrderItems(@NotNull List<CartItem> cartItems, Order placedOrder) {
        return cartItems
                .stream()
                .map(cartItem -> (cartItem.toOrderItem(placedOrder)))
                .toList();
    }

    @Override
    public void checkoutOrder(@NotNull List<CartItem> cartItems, Cart cart) {
        User user = cart.getUser();
        log.info("Placing order for user: {}", user.getEmail());
        Order order = new Order(user, PaymentStatus.PAID, LocalDate.now());
        Order placedOrder = orderRepository.save(order);

        List<OrderItem> orderItemStream = convertCartItemsToOrderItems(cartItems, placedOrder);
        orderItemRepository.saveAll(orderItemStream);

        deliveryService.createDelivery(placedOrder, cart.getUser().getUserDetail().getAddress().toString());
    }

    @Override
    public ResponseDto<List<OrderItemResponse>> getAllOrders() {
        try {
            User user = UserUtils.getAuthenticatedUser();
            log.info("Getting all orders for user: {}", user.getEmail());
            List<Order> orderList = getOrdersByUser(user);
            List<OrderItemResponse> orderItems = new ArrayList<>();
            if (!orderList.isEmpty()) {
                for (Order order : orderList) {
                    List<OrderItem> items = orderItemRepository.findAllByOrder(order).orElseGet(List::of);
                    List<OrderItemResponse> orderItemResponses = items
                            .stream()
                            .map(OrderItemResponse::fromOrderItem)
                            .map(this::mapOrderItemResponse)
                            .toList();
                    orderItems.addAll(orderItemResponses);
                }
                log.info("All orders fetched successfully: {}", orderItems);
                return ResponseDto.success(orderItems, "All orders fetched successfully");
            }
            return ResponseDto.success(Collections.emptyList(), "No orders found");
        } catch (Exception e) {
            log.error("Error while fetching all orders: {}", e.getMessage());
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Error while fetching all orders");
        }
    }


    @Override
    @ROLE_BUSINESS
    public ResponseDto<List<OrderItemResponse>> getOrdersForMenuItems(List<MenuItem> menuItems) {
        log.info("Getting all orders for business");
        try {
            var orderItems = orderItemRepository.findAllByItemIn(menuItems)
                    .orElseGet(List::of);

            var orderItemResponses = orderItems
                    .stream()
                    .map(OrderItemResponse::fromOrderItem)
                    .map(this::mapOrderItemResponse)
                    .toList();
            return ResponseDto.success(orderItemResponses, "All orders fetched successfully");
        } catch (Exception e) {
            log.error("Error while fetching all orders: {}", e.getMessage());
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Error while fetching all orders");
        }
    }

    /**
     * Get all orders for a user
     */
    private List<Order> getOrdersByUser(User user) {
        return orderRepository.findAllByCustomer(user)
                .orElseGet(() -> {
                    log.info("No orders found for user: {}", user.getEmail());
                    return List.of();
                });
    }

    @NotNull
    private OrderItemResponse mapOrderItemResponse(OrderItemResponse orderItemResponse) {
        MenuItemDto menuItem = orderItemResponse.getMenuItem();
        String imageUrl = s3Service.generatePreSignedUrl(menuItem.image());
        MenuItemDto menuItemDto = new MenuItemDto(menuItem, imageUrl);
        orderItemResponse.setMenuItem(menuItemDto);
        return orderItemResponse;
    }

}
