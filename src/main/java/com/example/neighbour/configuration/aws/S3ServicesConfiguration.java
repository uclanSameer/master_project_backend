package com.example.neighbour.configuration.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3ServicesConfiguration {

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;


    @Bean
    public S3Client createS3Client() {
        return S3Client.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey))
                .build();
    }

    @Bean
    public S3Presigner createS3Presigner() {
        return S3Presigner.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey))
                .build();
    }



}
