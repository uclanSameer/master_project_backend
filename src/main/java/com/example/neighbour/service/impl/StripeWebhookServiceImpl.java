package com.example.neighbour.service.impl;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.User;
import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.TransactionsDto;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.enums.Currency;
import com.example.neighbour.enums.TransactionMethod;
import com.example.neighbour.enums.TransactionStatus;
import com.example.neighbour.exception.ErrorResponseException;
import com.example.neighbour.service.CartService;
import com.example.neighbour.service.StripeWebhookService;
import com.example.neighbour.service.TransactionService;
import com.example.neighbour.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.Transfer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.utils.IoUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackOn = {
        StripeException.class,
        Exception.class
})
public class StripeWebhookServiceImpl implements StripeWebhookService {

    private final CartService cartService;
    private final UserService userService;

    private final TransactionService transactionService;

    @Value("${stripe.key.secret}")
    private String secretKey;

    @Value("${transaction.success.url}")
    private String successUrl;

    @Value("${transaction.cancel.url}")
    private String cancelUrl;

    @Value("${webhook.secret}")
    private String webhookSecret;

    @Override
    public ResponseDto<CartTotalDto> handleWebhook(HttpServletRequest request) throws StripeException, IOException {
        Stripe.apiKey = secretKey;
        String requestBody = IoUtils.toUtf8String(request.getInputStream());
        String sigHeader = request.getHeader("Stripe-Signature");
        Event event = Webhook.constructEvent(requestBody, sigHeader, webhookSecret);

        StripeObject stripeObject = event.getDataObjectDeserializer().getObject()
                .orElseThrow(() -> new ErrorResponseException(500, "Error occurred while retrieving session object"));
        if (event.getType().equals("checkout.session.completed")) {
            return handleCheckoutCompletedEvent((Session) stripeObject);
        }
        if (event.getType().equals("account.updated")) {
            return handleAccountUpdatedEvent((Account) stripeObject);
        }
        return null;
    }

    @Nullable
    private ResponseDto<CartTotalDto> handleAccountUpdatedEvent(Account stripeObject) {
        Account account = stripeObject;

        String accountId = account.getId();
        userService.updateBusinessAccountAsVerified(accountId);

        return null;
    }

    private ResponseDto<CartTotalDto> handleCheckoutCompletedEvent(Session stripeObject) throws StripeException {

        String sessionId = stripeObject.getId();
        Session session = Session.retrieve(sessionId);

        Map<String, String> metadata = session.getMetadata();
        String cartId = metadata.get("cartId");

        Cart cart = cartService.getCartByCartId(Integer.parseInt(cartId));
        if (cart == null) {
            throw new ErrorResponseException(404, "Cart not found");
        }
        User user = cart.getUser();

        Map<Business, BigDecimal> amountToPayToBusiness = cartService.getAmountToPayToBusiness(cartId);

        payToBusinessAndAddTransactionRecord(user, amountToPayToBusiness);

        return cartService.checkoutCart(cartId);
    }

    private void payToBusinessAndAddTransactionRecord(User user, Map<Business, BigDecimal> amountToPayToBusiness) throws StripeException {
        for (Map.Entry<Business, BigDecimal> entry : amountToPayToBusiness.entrySet()) {
            Business business = entry.getKey();
            String accountId = business.getAccountId();
            BigDecimal amount = entry.getValue();
            payToAccount(accountId, amount);

            createTransaction(user, business, amount);
        }
    }

    private void createTransaction(User user, Business business, BigDecimal amount) {
        TransactionsDto transactionsDto = TransactionsDto.builder()
                .amount(amount)
                .fromUser(user)
                .toBusiness(business)
                .transactionCurrency(Currency.GBP)
                .transactionDate(LocalDate.now())
                .transactionFee(BigDecimal.ZERO)
                .transactionMethod(TransactionMethod.STRIPE)
                .transactionStatus(TransactionStatus.SUCCESSFUL)
                .build();

        transactionService.createTransaction(transactionsDto);
    }


    public void payToAccount(String accountId, BigDecimal amount) throws StripeException {
        log.info("Paying to account: {} amount: {}", accountId, amount);
        Stripe.apiKey = secretKey;
        Map<String, Object> params = Map.of(
                "amount", amount,
                "currency", "gbp",
                "destination", accountId);
        Transfer.create(params);
    }


}
