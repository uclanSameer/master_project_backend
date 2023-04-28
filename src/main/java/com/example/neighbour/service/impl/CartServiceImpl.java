package com.example.neighbour.service.impl;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.MenuItem;
import com.example.neighbour.data.User;
import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.data.cart.CartItem;
import com.example.neighbour.data.cart.CartTotal;
import com.example.neighbour.dto.MenuItemDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartInfo;
import com.example.neighbour.dto.cart.CartItemDto;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.dto.order.OrderItemDto;
import com.example.neighbour.enums.PaymentStatus;
import com.example.neighbour.repositories.CartItemRepository;
import com.example.neighbour.repositories.CartRepository;
import com.example.neighbour.repositories.MenuRepository;
import com.example.neighbour.service.CartService;
import com.example.neighbour.service.CartTotalService;
import com.example.neighbour.service.OrderService;
import com.example.neighbour.service.ReceiptService;
import com.example.neighbour.service.aws.S3Service;
import com.example.neighbour.utils.GeneralStringConstants;
import com.example.neighbour.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.neighbour.utils.GeneralStringConstants.CART_NOT_FOUND;
import static com.example.neighbour.utils.GeneralStringConstants.SUCCESS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final MenuRepository menuItemRepository;

    private final S3Service s3Service;
    private final OrderService orderService;

    private final CartTotalService cartTotalService;

    private final ReceiptService  receiptService;

    @Transactional
    @Override
    public ResponseDto<CartTotalDto> addItemToCart(OrderItemDto orderItem) {
        try {

            int quantity = orderItem.quantity();
            int menuItemId = orderItem.menuId();
            log.info("Adding item to cart");
            User user = UserUtils.getAuthenticatedUser();
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            // Get cart for user or create new one if not found
            Cart cart = cartRepository.findByUserId(user.getId())
                    .orElseGet(() -> createNewCart(user));

            CartItem cartItem = new CartItem(
                    cart,
                    menuItem,
                    quantity);

            updateCartItems(quantity, menuItemId, cart, cartItem);

            log.info("Item added to cart");

            BigDecimal total = calculateTotal(cart.getId());

            CartTotal cartTotal = cartTotalService.saveCartTotal(cart, total);

            return ResponseDto.success(cartTotal.toDto(), SUCCESS);
        } catch (Exception e) {
            log.error("Error while adding item to cart", e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    @Transactional
    @Override
    public ResponseDto<CartTotalDto> removeItemFromCart(OrderItemDto orderItem) {
        try {
            int quantity = orderItem.quantity();
            int menuItemId = orderItem.menuId();

            log.info("Removing item from cart");
            Cart cart = getCurrentCart();

            CartItem cartItem = cartItemRepository
                    .findByCartIdAndItemId(cart.getId(), menuItemId)
                    .orElseThrow(() -> new RuntimeException("Item not found in cart"));

            CartTotalDto cartTotalDto = null;

            if (cartItem.getQuantity() == quantity) {
                // delete the cart item if the quantity is same as the quantity to be removed
                cartItemRepository.delete(cartItem);
                cartTotalService.deductFromCartTotal(cart,
                        cartItem.getItem().getPrice().multiply(BigDecimal.valueOf(quantity)));
            } else {
                cartTotalDto = deductCartQuantity(quantity, cart, cartItem);
            }
            return ResponseDto.success(cartTotalDto, SUCCESS);
        } catch (Exception e) {
            log.error("Error while removing item from cart", e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, GeneralStringConstants.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    @Override
    public ResponseDto<String> clearCart() {
        try {
            log.info("Clearing cart");
            Cart cart = getCurrentCart();

            cartItemRepository.deleteAllByCartId(cart.getId());
            cartTotalService.deleteCartTotal(cart.getId());
            return ResponseDto.success("Cart cleared", SUCCESS);
        } catch (Exception e) {
            log.error("Error while clearing cart", e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, GeneralStringConstants.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    @Override
    public ResponseDto<CartTotalDto> checkoutCart() {
        try {
            log.info("Checking out cart");
            Cart cart = getCurrentCart();

            return checkOut(cart);
        } catch (Exception e) {
            log.error("Error while checking out cart", e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, GeneralStringConstants.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public ResponseDto<CartTotalDto> checkoutCart(String cartId) {
        log.info("Checking out cart: {}", cartId);
        Cart cart = cartRepository.findById(Integer.parseInt(cartId))
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, CART_NOT_FOUND));
        return checkOut(cart);
    }

    @Override
    public Cart createNewCart(User user) {
        log.info("Creating new cart for user: {}", user);
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }

    @Override
    public ResponseDto<CartInfo> getCartInfo() {
        try {
            Cart cart = getCurrentCart();
            List<CartItemDto> itemList = getCartItemList(cart.getId());
            CartTotal cartTotal = cartTotalService.getCartTotal(cart.getId());

            CartInfo cartInfo = new CartInfo(cartTotal.toDto(), itemList);
            return ResponseDto.success(cartInfo, SUCCESS);
        } catch (Exception e) {
            log.error("Error while getting cart items", e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, GeneralStringConstants.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public ResponseDto<List<CartItemDto>> getCartInfo(int cartId) {
        try {
            List<CartItemDto> list = getCartItemList(cartId);
            return ResponseDto.success(list, SUCCESS);
        } catch (Exception e) {
            log.error("Error while getting cart items", e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, GeneralStringConstants.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public Cart getCurrentCart() {
        User user = UserUtils.getAuthenticatedUser();
        return cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, CART_NOT_FOUND));
    }

    public Cart getCartByCartId(int cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, CART_NOT_FOUND));
    }


    @Override
    public Map<Business, BigDecimal> getAmountToPayToBusiness(String cartId) {
        log.info("Getting amount to pay to business for cart: {}", cartId);

        List<CartItem> cartItems = cartItemRepository.findAllByCartId(Integer.parseInt(cartId));

        Map<Business, BigDecimal> amountToPayToBusiness = new HashMap<>();
        cartItems
                .forEach(cartItem -> {
                    MenuItem menuItem = cartItem.getItem();
                    Integer quantity = cartItem.getQuantity();
                    Business business = menuItem.getBusiness();
                    String businessId = business.getAccountId();

                    BigDecimal price = menuItem.getPrice().multiply(new BigDecimal(quantity));

                    if (amountToPayToBusiness.containsKey(business)) {
                        BigDecimal amount = amountToPayToBusiness.get(business);
                        amountToPayToBusiness.put(business, amount.add(price));
                    } else {
                        amountToPayToBusiness.put(business, price);
                    }
                });

        return amountToPayToBusiness;
    }


    @NotNull
    private ResponseDto<CartTotalDto> checkOut(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            return ResponseDto.success(null, "Cart is empty");
        }
        orderService.checkoutOrder(cartItems, cart);

        receiptService.sendReceipt(cartItems, cart.getUser().getEmail());

        log.info("Order placed for cart: {}", cartItems);

        CartTotal cartTotal = cartTotalService.checkoutCartTotal(cart, PaymentStatus.PAID);

        cartItemRepository.deleteAllByCartId(cart.getId());
        return ResponseDto.success(cartTotal.toDto(), SUCCESS);
    }

    @NotNull
    private List<CartItemDto> getCartItemList(int cartId) {
        log.info("Getting cart items for cart: {}", cartId);
        return cartItemRepository.findAllByCartId(cartId)
                .stream()
                .map(this::generateCartItemDto)
                .toList();
    }

    private void updateCartItems(int quantity, int menuItemId, Cart cart, CartItem cartItem) {
        cartItemRepository.findByCartIdAndItemId(cart.getId(), menuItemId)
                .ifPresentOrElse(
                        item -> {
                            item.setQuantity(item.getQuantity() + quantity);
                            cartItemRepository.save(item);
                        },
                        () -> cartItemRepository.save(cartItem));
    }

    private CartTotalDto deductCartQuantity(int quantity, Cart cart, CartItem cartItem) {

        if (cartItem.getQuantity() < quantity) {
            throw new ResponseStatusException(BAD_REQUEST, "Quantity to be removed is more than the quantity in cart");
        }
        cartItem.setQuantity(cartItem.getQuantity() - quantity);
        cartItemRepository.save(cartItem);
        CartTotal cardTotal = cartTotalService.deductFromCartTotal(cart,
                cartItem.getItem().getPrice().multiply(BigDecimal.valueOf(quantity)));

        return cardTotal.toDto();
    }

    @NotNull
    private CartItemDto generateCartItemDto(CartItem cartItem) {
        MenuItem menuItem = cartItem.getItem();
        String imageUrl = null;
        if (menuItem.getImage() != null) {
            imageUrl = s3Service.generatePreSignedUrl(menuItem.getImage());
        }
        MenuItemDto menuItemDto = new MenuItemDto(menuItem, imageUrl);
        log.info("Cart item: {}", menuItemDto);
        return new CartItemDto(menuItemDto, cartItem.getQuantity());
    }

    private BigDecimal calculateTotal(int cartId) {
        List<CartItem> cartItems = cartItemRepository
                .findAllByCartId(cartId);
        return cartItems
                .stream()
                .map(cartItem -> cartItem.getItem().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
