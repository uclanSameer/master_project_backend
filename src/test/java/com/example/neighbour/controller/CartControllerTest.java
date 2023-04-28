package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartInfo;
import com.example.neighbour.dto.cart.CartInfoTest;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.dto.cart.CartTotalDtoTest;
import com.example.neighbour.dto.order.OrderItemDto;
import com.example.neighbour.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;
    @InjectMocks
    private CartController cartController;

    @Test
    void testAddToCart() {
        OrderItemDto orderItemDto = new OrderItemDto(
                1, 1
        );
        CartTotalDto cartTotalDto = CartTotalDtoTest.getCartTotalDto();
        when(cartService.addItemToCart(orderItemDto)).thenReturn(ResponseDto.success(cartTotalDto, "Item added to cart successfully"));

        ResponseDto<CartTotalDto> cartTotalDtoResponseDto = cartController.addToCart(orderItemDto);

        assert (cartTotalDtoResponseDto.getData().equals(cartTotalDto));
    }


    @Test
    void testGetCartInfo() {
        CartInfo cartInfo = CartInfoTest.getCartInfo();
        when(cartService.getCartInfo()).thenReturn(ResponseDto.success(cartInfo, "Cart info retrieved successfully"));

        ResponseDto<CartInfo> cartInfoResponseDto = cartController.getCartInfo();

        assert (cartInfoResponseDto.getData().equals(cartInfo));
    }

    @Test
    void testCheckOut(){
        CartTotalDto cartTotalDto = CartTotalDtoTest.getCartTotalDto();
        when(cartService.checkoutCart()).thenReturn(ResponseDto.success(cartTotalDto, "Cart checked out successfully"));

        ResponseDto<CartTotalDto> cartTotalDtoResponseDto = cartController.checkoutCart();

        assert (cartTotalDtoResponseDto.getData().equals(cartTotalDto));
    }

    @Test
    void testRemoveFromCart(){
        OrderItemDto orderItemDto = new OrderItemDto(
                1, 1
        );
        CartTotalDto cartTotalDto = CartTotalDtoTest.getCartTotalDto();
        when(cartService.removeItemFromCart(orderItemDto)).thenReturn(ResponseDto.success(cartTotalDto, "Item removed from cart successfully"));

        ResponseDto<CartTotalDto> cartTotalDtoResponseDto = cartController.removeFromCart(orderItemDto);

        assert (cartTotalDtoResponseDto.getData().equals(cartTotalDto));
    }
}