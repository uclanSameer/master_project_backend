package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartInfo;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.dto.order.OrderItemDto;
import com.example.neighbour.service.CartService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseDto<CartTotalDto> addToCart(@RequestBody OrderItemDto orderItem) {
        log.info("Adding to cart with order item: {}", orderItem);
        return cartService.addItemToCart(orderItem);
    }

    @PostMapping("/info")
    public ResponseDto<CartInfo> getCartInfo() {
        log.info("Getting cart info ");
        return cartService.getCartInfo();
    }

    @PostMapping("/checkout")
    public ResponseDto<CartTotalDto> checkoutCart() {
        log.info("Checking out cart ");
        return cartService.checkoutCart();
    }

    @DeleteMapping("/clear")
    public ResponseDto<String> clearCart() {
        log.info("Clearing cart ");
        return cartService.clearCart();
    }

    @DeleteMapping("/remove")
    public ResponseDto<CartTotalDto> removeFromCart(@RequestBody OrderItemDto orderItem) {
        log.info("Removing from cart with order item: {}", orderItem);
        return cartService.removeItemFromCart(orderItem);
    }

}
