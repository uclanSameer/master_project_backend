package com.example.neighbour.configuration;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfiguration {
    public StripeConfiguration(
            @Value("${stripe.key.secret}") String secretKey
    ) {
        Stripe.apiKey = secretKey;
    }
}
