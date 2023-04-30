package com.example.neighbour.service.impl;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.BusinessTest;
import com.example.neighbour.data.MenuItemTest;
import com.example.neighbour.data.cart.*;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.dto.order.OrderItemDto;
import com.example.neighbour.dto.order.OrderItemDtoTest;
import com.example.neighbour.enums.PaymentStatus;
import com.example.neighbour.repositories.CartItemRepository;
import com.example.neighbour.repositories.CartRepository;
import com.example.neighbour.repositories.MenuRepository;
import com.example.neighbour.service.CartTotalService;
import com.example.neighbour.service.OrderService;
import com.example.neighbour.service.ReceiptService;
import com.example.neighbour.service.aws.S3Service;
import com.example.neighbour.utils.GeneralStringConstants;
import com.example.neighbour.utils.UserUtilsTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository mockCartRepository;

    @Mock
    private CartItemRepository mockCartItemRepository;

    @Mock
    private MenuRepository mockMenuRepository;

    @Mock
    private S3Service mockS3Service;

    @Mock
    private OrderService mockOrderService;

    @Mock
    private CartTotalService mockCartTotalService;

    @Mock
    private ReceiptService mockReceiptService;

    @InjectMocks
    private CartServiceImpl cartServiceImplUnderTest;

    @BeforeAll
    static void setUp() {
        UserUtilsTest.SET_NORMAL_USER_AUTHENTICATION();
    }

    @Test
    void testAddItemToCart() {
        // Setup

        when(mockMenuRepository.findById(any())).thenReturn(Optional.of(MenuItemTest.createMenuItem()));

        when(mockCartRepository.findByUserId(any())).thenReturn(Optional.of(CartTest.createCart()));

        when(mockCartItemRepository.findByCartIdAndItemId(any(), any())).thenReturn(Optional.of(
                CartItemTest.createCartItem()
        ));

        when(mockCartItemRepository.save(any())).thenReturn(CartItemTest.createCartItem());

        when(mockCartTotalService.saveCartTotal(any(), any())).thenReturn(
                CartTotalTest.createCartTotal()
        );

        OrderItemDto orderItemDto = new OrderItemDto(1, 1);

        // Run the test

        ResponseDto<CartTotalDto> cartTotalDtoResponseDto = cartServiceImplUnderTest.addItemToCart(orderItemDto);

        // Verify the results
        assertEquals(GeneralStringConstants.SUCCESS, cartTotalDtoResponseDto.getMessage(), GeneralStringConstants.SUCCESS);
    }


    @Test
    void testRemoveItemFromCart() {
        OrderItemDto orderItemDto = OrderItemDtoTest.createOrderItemDto();

        when(mockCartRepository.findByUserId(any())).thenReturn(Optional.of(CartTest.createCart()));

        when(mockCartItemRepository.findByCartIdAndItemId(any(), any())).thenReturn(Optional.of(
                CartItemTest.createCartItem()
        ));

        CartTotal cartTotal = CartTotalTest.createCartTotal();

        when(mockCartTotalService.deductFromCartTotal(any(), any())).thenReturn(
                cartTotal
        );

        ResponseDto<CartTotalDto> cartTotalDtoResponseDto = cartServiceImplUnderTest.removeItemFromCart(orderItemDto);

        assertEquals(
                GeneralStringConstants.SUCCESS,
                cartTotalDtoResponseDto.getMessage(),
                GeneralStringConstants.SUCCESS);
    }

    @Test
    void testClearCart() {
        when(mockCartRepository.findByUserId(any())).thenReturn(Optional.of(CartTest.createCart()));

        doNothing().when(mockCartItemRepository).deleteAllByCartId(anyInt());

        doNothing().when(mockCartTotalService).deleteCartTotal(anyInt());

        ResponseDto<String> cartTotalDtoResponseDto = cartServiceImplUnderTest.clearCart();

        assertEquals(
                GeneralStringConstants.SUCCESS,
                cartTotalDtoResponseDto.getMessage(),
                GeneralStringConstants.SUCCESS);
    }

    @Test
    void testCheckoutCart() {
        when(mockCartRepository.findByUserId(anyInt())).thenReturn(Optional.of(CartTest.createCart()));

        when(mockCartItemRepository.findAllByCartId(anyInt())).thenReturn((
                List.of(CartItemTest.createCartItem())
        ));

        doNothing().when(mockOrderService).checkoutOrder(anyList(), any(Cart.class));

        doNothing().when(mockReceiptService).sendReceipt(anyList(), anyString());

        when(mockCartTotalService.checkoutCartTotal(any(), eq(PaymentStatus.PAID)))
                .thenReturn(CartTotalTest.createCartTotal());

        ResponseDto<CartTotalDto> cartTotalDtoResponseDto = cartServiceImplUnderTest.checkoutCart();

        assertEquals(
                GeneralStringConstants.SUCCESS,
                cartTotalDtoResponseDto.getMessage(),
                GeneralStringConstants.SUCCESS);
    }

    @Test
    public void testGetAmountToPayToBusiness() {
        when(mockCartItemRepository.findAllByCartId(anyInt())).thenReturn((
                List.of(CartItemTest.createCartItem())
        ));

        Map<Business, BigDecimal> amountsToPay = cartServiceImplUnderTest.getAmountToPayToBusiness("1");

        assertEquals(
                "Amounts to pay should be 1",
                1,
                amountsToPay.size()
        );
    }
}