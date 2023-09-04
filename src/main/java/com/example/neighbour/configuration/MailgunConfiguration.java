package com.example.neighbour.configuration;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailgunConfiguration {

    @Value("${MAILGUN_PRIVATE_KEY}")
    private String apiKey;

    @Value("${BASE_URL}")
    private String baseUrl;

    @Bean()
    public MailgunMessagesApi mailgunMessagesApi() {
        MailgunClient.MailgunClientBuilder clientBuilder = MailgunClient.config(
                baseUrl,
                apiKey
        );

        return clientBuilder.createApi(MailgunMessagesApi.class);
    }
}
