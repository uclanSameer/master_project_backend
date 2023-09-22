package com.example.neighbour.configuration.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3ServicesConfiguration {

    @Value("${aws.region}")
    private String region;


    @Bean
    public S3Client createS3Client(AwsCredentials credentials) {
        return S3Client.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .credentialsProvider(() -> credentials)
                .build();
    }

    @Bean
    public S3Presigner createS3Presigner(AwsCredentials credentials) {
        return S3Presigner.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .credentialsProvider(() -> credentials)
                .build();
    }


}
