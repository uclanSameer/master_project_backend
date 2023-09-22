package com.example.neighbour.service;

import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.data.cart.CartTotal;
import com.example.neighbour.enums.PaymentStatus;

import java.math.BigDecimal;

public interface CartTotalService {

    /**
     * Adds cart total to the database
     *
     * @param cart  - cart
     * @param total - total
     * @return - cart total
     */
    CartTotal saveCartTotal(Cart cart, BigDecimal total);

    /**
     * Deduct amount from the  cart total in the database
     *
     * @param cart  - cart
     * @param total - total
     * @return - cart total
     */
    CartTotal deductFromCartTotal(Cart cart, BigDecimal total);

    /**
     * Used when the payment is done for the cart
     *
     * @param cart   - cart
     * @param status - payment status
     * @return - cart total
     */
    CartTotal checkoutCartTotal(Cart cart, PaymentStatus status);

    /**
     * Gets the cart total from the database for the given cart id
     *
     * @param cartId - cart id
     * @return - cart total
     */
    CartTotal getCartTotal(int cartId);

    /**
     * Deletes the cart total from the database for the given cart id
     *
     * @param cartId - cart id
     */
    void deleteCartTotal(int cartId);
}
