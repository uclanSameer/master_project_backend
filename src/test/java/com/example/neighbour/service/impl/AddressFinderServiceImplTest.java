package com.example.neighbour.service.impl;

import com.example.neighbour.dto.myptv.AddressResponse;
import com.example.neighbour.dto.myptv.AddressResponseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(MockitoExtension.class)
class AddressFinderServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AddressFinderServiceImpl addressFinderService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(addressFinderService, "apiKey", "123");
        ReflectionTestUtils.setField(addressFinderService, "apiUrl", "http://localhost:8080");
    }

    @Test
    void verifyGetListOfAddress() {
        var postCode = "PR1 1AA";

        when(
                restTemplate.exchange(anyString(), eq(GET), any(HttpEntity.class), eq(AddressResponse.class))
        ).thenReturn(
                ResponseEntity.of(
                        Optional.of(
                                AddressResponseTest.getAddressResponse()
                        )
                )
        );

        var response = addressFinderService.getListOfAddress(postCode);

        assert response != null;

        assert response.isSuccess();

        assertEquals(response.getMessage(), "Successfully retrieved address");

        assertEquals(response.getData().size(), 1);

        assertEquals(response.getData().get(0).formattedAddress(), "formattedAddress");

    }

    @Test
    void verifyForInvalidPostCode() {
        var postCode = "bad bad code";

        assertThrows(
                ErrorResponseException.class,
                () -> addressFinderService.getListOfAddress(postCode)
        );
    }

    @Test
    void verifyForEmptyResponse() {
        var postCode = "PR1 1AA";

        when(
                restTemplate.exchange(anyString(), eq(GET), any(HttpEntity.class), eq(AddressResponse.class))
        ).thenReturn(
                ResponseEntity.of(
                        Optional.empty()
                )
        );

        assertThrows(
                ErrorResponseException.class,
                () -> addressFinderService.getListOfAddress(postCode),
                "Unable to retrieve address"
        );

    }
}