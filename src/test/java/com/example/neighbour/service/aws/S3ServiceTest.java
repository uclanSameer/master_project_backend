package com.example.neighbour.service.aws;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private S3Client s3Client;
    @Mock
    private S3Presigner s3Presigner;

    @InjectMocks
    private S3ServiceImpl s3ServiceImpl;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(s3ServiceImpl, "bucketName", "testBucket");
    }

    @Test
    void uploadFile() {
        String base64File = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";

        s3ServiceImpl.uploadFile("testKey", base64File);

        verify(s3Client, times(1)).putObject(any(Consumer.class), any(RequestBody.class));
    }

    @Test
    void generatePreSignedUrl() throws MalformedURLException {

        PresignedGetObjectRequest presignedGetObjectRequest = mock(PresignedGetObjectRequest.class);
        String urlValue = "https://test.com";
        when(presignedGetObjectRequest.url()).thenReturn(new URL(urlValue));

        when(s3Client.headObject(any((Consumer.class))))
                .thenReturn(HeadObjectResponse
                        .builder()
                        .contentLength(1L)
                        .build());

        when(s3Presigner.presignGetObject((GetObjectPresignRequest) any())).thenReturn(presignedGetObjectRequest);

        String url = s3ServiceImpl.generatePreSignedUrl("testKey");

        assertNotNull(url);

        assertEquals(urlValue, url);
    }

    @Test
    void testForCheckIfFileExists(){
        when(s3Client.headObject(any(Consumer.class))).thenReturn(HeadObjectResponse.
                builder()
                        .contentLength(1L)
                .build());
        boolean result = s3ServiceImpl.checkIfFileExists("testKey");
        assertTrue(result);
    }

    @Test
    void testForCheckIfFileExistsForFailureCondition(){
        when(s3Client.headObject(any(Consumer.class))).thenReturn(HeadObjectResponse.
                builder()
                        .contentLength(0L)
                .build());
        boolean result = s3ServiceImpl.checkIfFileExists("testKey");
        assertFalse(result);
    }

    @Test
    void testForCheckIfFileExistsForExceptionCondition(){
        when(s3Client.headObject(any(Consumer.class))).thenThrow(S3Exception.class);
        boolean result = s3ServiceImpl.checkIfFileExists("testKey");
        assertFalse(result);
    }
}