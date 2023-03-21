package com.example.neighbour.service.impl;

import com.example.neighbour.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;
    private final S3Presigner preSigner;
    @Value("${aws.bucketName}")
    private String bucketName;

    @Override
    public void uploadFile(String keyName, String base64File) {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64File);
            RequestBody requestBody = RequestBody.fromBytes(bytes);
            s3Client.putObject(builder -> builder
                    .bucket(bucketName)
                    .key(keyName), requestBody);
        } catch (S3Exception e) {
            log.error("Error while uploading file to S3 with message: {}", e.getMessage(), e);
        }
    }

    @Override
    public String generatePreSignedUrl(String keyName) {
        try {
            if (!checkIfFileExists(keyName)) {
                log.error("File with key: {} does not exist in S3", keyName);
                return null;
            }
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                   .bucket(bucketName)
                   .key(keyName)
                   .build();

                   GetObjectPresignRequest putObjectPresignRequest = GetObjectPresignRequest
                    .builder()
                    .signatureDuration(Duration.ofMinutes(15))
                    .getObjectRequest(getObjectRequest).build();

            return preSigner
                    .presignGetObject(putObjectPresignRequest)
                    .url().toString();
        } catch (S3Exception e) {
            log.error("Error while generating pre-signed url with message: {}", e.getMessage(), e);
            return null;
        }

    }

    @Override
    public boolean checkIfFileExists(String keyName) {
        try {
            log.info("Checking if file exists in S3 with key: {}", keyName);
            return s3Client.headObject(builder -> builder.bucket(bucketName)
                            .key(keyName))
                    .contentLength() > 0;
        } catch (S3Exception e) {
            log.error("Error while checking if file exists with message: {}", e.getMessage(), e);
            return false;
        }
    }
}
