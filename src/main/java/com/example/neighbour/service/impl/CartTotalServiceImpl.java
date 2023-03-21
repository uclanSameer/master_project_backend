package com.example.neighbour.service.impl;

import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.data.cart.CartTotal;
import com.example.neighbour.enums.PaymentStatus;
import com.example.neighbour.repositories.CartTotalRepository;
import com.example.neighbour.service.CartTotalService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static com.example.neighbour.utils.GeneralStringConstants.CART_TOTAL_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
@Slf4j
public class CartTotalServiceImpl implements CartTotalService {

    private final CartTotalRepository cartTotalRepository;

    @Transactional
    @Override
    public CartTotal saveCartTotal(Cart cart, BigDecimal total) {
        log.info("Saving cart total");
        CartTotal cartTotal = cartTotalRepository.findByCartId(cart.getId())
                .orElseGet(() -> {
                    log.info(CART_TOTAL_NOT_FOUND + ", creating new cart total");
                    CartTotal newCartTotal = new CartTotal();
                    newCartTotal.setCart(cart);
                    return newCartTotal;
                });
        cartTotal.setTotalCost(total);
        cartTotal.setTaxes(BigDecimal.ZERO);
        cartTotal.setDeliveryCharges(BigDecimal.ZERO);
        cartTotal.setStatus(PaymentStatus.UNPAID);
        return cartTotalRepository.save(cartTotal);
    }

    @Transactional
    @Override
    public CartTotal checkoutCartTotal(Cart cart, PaymentStatus status) {
        log.info("Updating cart total status: {}", status);
        CartTotal cartTotal = getCartTotal(cart.getId());
        cartTotal.setStatus(status);
        cartTotal.setTaxes(BigDecimal.ZERO);
        cartTotal.setDeliveryCharges(BigDecimal.ZERO);
        cartTotal.setTotalCost(BigDecimal.ZERO);
        return cartTotalRepository.save(cartTotal);
    }

    @Override
    public CartTotal getCartTotal(int cartId) {
        log.info("Getting cart total");
        return cartTotalRepository.findByCartId(cartId)
                .orElseThrow(() -> new RuntimeException(CART_TOTAL_NOT_FOUND));
    }

    @Override
    public CartTotal deductFromCartTotal(Cart cart, BigDecimal amount) {
        log.info("Deducting {} from cart total", amount);
        CartTotal cartTotal = getCartTotal(cart.getId());

        if (cartTotal.getTotalCost().compareTo(amount) >= 0) {
            cartTotal.setTotalCost(cartTotal.getTotalCost().subtract(amount));
            return cartTotalRepository.save(cartTotal);
        }
        throw new ResponseStatusException(BAD_REQUEST, "Cart total is less than the amount to be deducted");
    }

    @Override
    public void deleteCartTotal(int cartId) {
        log.info("Deleting cart total");
        cartTotalRepository.deleteByCartId(cartId);
    }

}
