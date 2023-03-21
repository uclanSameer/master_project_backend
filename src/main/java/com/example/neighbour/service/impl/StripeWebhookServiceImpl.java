package com.example.neighbour.service.impl;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.exception.ErrorResponseException;
import com.example.neighbour.service.CartService;
import com.example.neighbour.service.StripeWebhookService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.utils.IoUtils;

import java.io.IOException;
import java.math.BigDecimal;
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
            Session sessionObject = (Session) stripeObject;

            String sessionId = sessionObject.getId();
            Session session = Session.retrieve(sessionId);

            Map<String, String> metadata = session.getMetadata();
            String cartId = metadata.get("cartId");

            Map<String, BigDecimal> amountToPayToBusiness = cartService.getAmountToPayToBusiness(cartId);

            for (Map.Entry<String, BigDecimal> entry : amountToPayToBusiness.entrySet()) {
                String accountId = entry.getKey();
                BigDecimal amount = entry.getValue();
                payToAccount(accountId, amount);
            }

            return cartService.checkoutCart(cartId);
        }
        if (event.getType().equals("account.updated")) {
            // TODO handle account update and update business account as verified

            Account account = (Account) stripeObject;

            String accountId = account.getId();
            userService.updateBusinessAccountAsVerified(accountId);

            return null;
        }
        return null;
    }


    public void payToAccount(String accountId, BigDecimal amount) throws StripeException {
        log.info("Paying to account: {} amount: {}", accountId, amount);
        Stripe.apiKey = secretKey;
        Map<String, Object> params = Map.of(
                "amount", amount,
                "currency", "gbp",
                "destination", accountId,
                "transfer_group", "ORDER_95"
        );
        Transfer.create(params);
    }


}
