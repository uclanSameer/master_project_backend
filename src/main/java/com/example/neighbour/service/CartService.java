package com.example.neighbour.service;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.User;
import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartInfo;
import com.example.neighbour.dto.cart.CartItemDto;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.dto.order.OrderItemDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CartService {

    /**
     * Adds item to the cart
     *
     * @param orderItem - item to be added
     * @return - response dto
     */
    ResponseDto<CartTotalDto> addItemToCart(OrderItemDto orderItem);

    /**
     * Removes item from the cart
     *
     * @param orderItem - item to be removed
     * @return - response dto
     */
    ResponseDto<CartTotalDto> removeItemFromCart(OrderItemDto orderItem);

    /**
     * Clears the cart.
     *
     * @return - response dto
     */
    ResponseDto<String> clearCart();

    /**
     * Called when the user clicks on checkout button
     *
     * @return - response dto
     */
    ResponseDto<CartTotalDto> checkoutCart();

    ResponseDto<CartTotalDto> checkoutCart(String cartId);

    /**
     * Creates a new cart for the user if it does not exist.
     * Normally, a cart is created when the user signs up is done.
     * One cart per user.
     *
     * @param user - user for whom the cart is being created
     * @return - cart
     */
    Cart createNewCart(User user);

    /**
     * Gets the list of items in cart for the user for given cart id
     *
     * @param cartId - cart id
     * @return - cart
     */
    ResponseDto<List<CartItemDto>> getCartInfo(int cartId);

    /**
     * Gets all the cart information for the user for current logged in user
     *
     * @return - cart
     */
    ResponseDto<CartInfo> getCartInfo();

    /**
     * Gets the current cart for the user
     *
     * @return - cart
     */
    Cart getCurrentCart();

    Cart getCartByCartId(int cartId);


    /**
     * Gets the total amount to be paid by the user for the business
     *
     * @return - total amount
     */
    Map<Business, BigDecimal> getAmountToPayToBusiness(String cartId);


}
