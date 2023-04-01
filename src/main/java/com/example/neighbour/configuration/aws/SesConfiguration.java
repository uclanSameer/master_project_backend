package com.example.neighbour.configuration.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.sesv2.SesV2Client;

@Configuration
public class SesConfiguration {
    @Value("${aws.region}")
    private String region;

    @Bean
    public SesV2Client createSesClient(AwsCredentials credentials) {
        return SesV2Client.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .credentialsProvider(() -> credentials)
                .build();
    }
}
