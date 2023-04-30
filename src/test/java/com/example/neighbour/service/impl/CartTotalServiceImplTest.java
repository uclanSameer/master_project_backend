package com.example.neighbour.service.impl;

import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.data.cart.CartTest;
import com.example.neighbour.data.cart.CartTotal;
import com.example.neighbour.data.cart.CartTotalTest;
import com.example.neighbour.enums.PaymentStatus;
import com.example.neighbour.repositories.CartTotalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartTotalServiceImplTest {

    @Mock private CartTotalRepository cartTotalRepository;

    @InjectMocks private CartTotalServiceImpl cartTotalService;

    @Test
    void testSaveCartTotal() {
        Cart cart = CartTest.createCart();
        when(cartTotalRepository.findByCartId(cart.getId())).thenReturn(Optional.of(CartTotalTest.createCartTotal()));

        when(cartTotalRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        CartTotal cartTotal = cartTotalService.saveCartTotal(cart, BigDecimal.TEN);

        assertNotNull(cartTotal);
        assertEquals(cartTotal.getCart().getId(), cart.getId());
        assertEquals(cartTotal.getTotalCost(), BigDecimal.TEN);
        assertEquals(cartTotal.getStatus(), PaymentStatus.UNPAID);
    }


    @Test
    void testCheckoutCart(){

        Cart cart = CartTest.createCart();
        when(cartTotalRepository.findByCartId(cart.getId())).thenReturn(Optional.of(CartTotalTest.createCartTotal()));

        when(cartTotalRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        CartTotal cartTotal = cartTotalService.checkoutCartTotal(cart, PaymentStatus.PAID);

        assertNotNull(cartTotal);
        assertEquals(cartTotal.getStatus(), PaymentStatus.PAID);
        assertEquals(cartTotal.getTotalCost(), BigDecimal.ZERO);
    }


    @Test
    void verifyDeductingCartTotal(){
        Cart cart = CartTest.createCart();
        when(cartTotalRepository.findByCartId(cart.getId())).thenReturn(Optional.of(CartTotalTest.createCartTotal()));

        when(cartTotalRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        CartTotal cartTotal = cartTotalService.deductFromCartTotal(cart, BigDecimal.TEN);

        assertNotNull(cartTotal);
        assertEquals(cartTotal.getCart().getId(), cart.getId());

        // initially total cost was 10, and we deducted 10, so it should be 0

        assertEquals(cartTotal.getTotalCost(), BigDecimal.ZERO);
    }
}