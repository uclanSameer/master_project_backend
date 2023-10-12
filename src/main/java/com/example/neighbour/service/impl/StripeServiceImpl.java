package com.example.neighbour.service.impl;

import com.example.neighbour.data.cart.Cart;
import com.example.neighbour.dto.StripeAccountResponse;
import com.example.neighbour.dto.cart.CartItemDto;
import com.example.neighbour.exception.ErrorResponseException;
import com.example.neighbour.service.CartService;
import com.example.neighbour.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.checkout.Session;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackOn = {
        StripeException.class,
        Exception.class
})
public class StripeServiceImpl implements StripeService {

    private final CartService cartService;
    @Value("${stripe.key.secret}")
    private String secretKey;

    @Value("${transaction.success.url}")
    private String successUrl;

    @Value("${transaction.cancel.url}")
    private String cancelUrl;

    @Override
    public String generateCheckOutUrl() throws StripeException {
        Cart currentCart = cartService.getCurrentCart();
        String clientReferenceId = currentCart.getId().toString();
        Stripe.apiKey = secretKey;

        SessionCreateParams params = buildSessionCreateParams(clientReferenceId);

        Session session = Session.create(params);
        session.setClientReferenceId(clientReferenceId);

        return session.getUrl();
    }


    @Override
    public StripeAccountResponse registerBusinessAccount(String email) {
        try {
            Stripe.apiKey = secretKey;

            AccountCreateParams params = buildAccountCreateParams();

            Account account = Account.create(params);

            AccountLinkCreateParams accountLinkCreateParams = buildAccountLinkParams(account);

            AccountLink accountLink = AccountLink.create(accountLinkCreateParams);
            return new StripeAccountResponse(accountLink.getUrl(), account.getId());
        } catch (StripeException e) {
            log.error("Error occurred while creating business account: {}", e.getMessage());
            throw new ErrorResponseException(e.getStatusCode(), "Error occurred while creating business account");
        }
    }

    public String refreshAccountLink(String accountId){
        try {
            Account account = Account.retrieve(accountId);
            AccountLinkCreateParams accountLinkCreateParams = buildAccountLinkParams(account);

            AccountLink accountLink = AccountLink.create(accountLinkCreateParams);
            return accountLink.getUrl();
        } catch (StripeException e) {
            throw new ErrorResponseException(e.getStatusCode(), "Error occurred while retrieving account");
        }
    }

    private static AccountCreateParams buildAccountCreateParams() {
        return AccountCreateParams
                .builder()
                .setCountry("GB")
                .setType(AccountCreateParams.Type.EXPRESS)
                .setCapabilities(buildCapabilities())
                .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL)
                .setBusinessProfile(buildBusinessProfile())
                .build();
    }

    /**
     * Give card payments and transfers capabilities to the account
     */
    private static AccountCreateParams.Capabilities buildCapabilities() {
        return AccountCreateParams.Capabilities
                .builder()
                .setCardPayments(createCardPayments())
                .setTransfers(createTransfers())
                .build();
    }

    /**
     * This is the capability that will be enabled on the Stripe dashboard
     */
    private static AccountCreateParams.Capabilities.Transfers createTransfers() {
        return AccountCreateParams.Capabilities.Transfers.builder().setRequested(true).build();
    }

    /**
     * This is the capability that will be enabled on the Stripe dashboard
     */
    private static AccountCreateParams.Capabilities.CardPayments createCardPayments() {
        return AccountCreateParams.Capabilities.CardPayments
                .builder()
                .setRequested(true)
                .build();
    }

    private static AccountLinkCreateParams buildAccountLinkParams(Account account) {
        return AccountLinkCreateParams
                .builder()
                .setAccount(account.getId())
                .setRefreshUrl("http://localhost:8080/api/v1/webhook/account/refresh/" + account.getId())
                .setReturnUrl("http://localhost:4321/login")
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build();
    }

    /**
     * This is the business profile that will be displayed on the Stripe dashboard
     */
    private static AccountCreateParams.BusinessProfile buildBusinessProfile() {
        return AccountCreateParams
                .BusinessProfile
                .builder()
                .setUrl("https://www.linkedin.com/in/sameer-manandhar-52126a116/")
                .build();
    }

    private List<SessionCreateParams.LineItem> createSessionLineItem() {
        return cartService.getCartInfo()
                .getData().getItems().stream()
                .map(this::createSessionLineItem)
                .toList();
    }

    private SessionCreateParams.LineItem createSessionLineItem(CartItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.quantity())))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CartItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("GBP")
                .setUnitAmount((checkoutItemDto.item().price().longValue() * 100))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.item().name())
                                .addImage(checkoutItemDto.item().image())
                                .build()
                ).build();
    }

    private SessionCreateParams buildSessionCreateParams(String clientReferenceId) {
        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addAllLineItem(createSessionLineItem())
                .putMetadata("cartId", clientReferenceId)
                .build();
    }

}
