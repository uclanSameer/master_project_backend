package com.example.neighbour.service.impl;

import com.example.neighbour.dto.place.LocationResponse;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.myptv.AddressResponse;
import com.example.neighbour.dto.myptv.Location;
import com.example.neighbour.exception.ErrorResponseException;
import com.example.neighbour.service.AddressFinderService;
import com.example.neighbour.utils.GeneralStringConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressFinderServiceImpl implements AddressFinderService {

    @Value("${myptv.api.key}")
    private String apiKey;

    @Value("${myptv.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @Override
    @RegisterReflectionForBinding({AddressResponse.class})
    public ResponseDto<List<LocationResponse>> getListOfAddress(String postCode) {
        log.debug("Retrieving address for post code: {}", postCode);

        validatePostCode(postCode);

        HttpEntity<Object> httpEntity = buildHttpHeader();
        String url = MessageFormat.format("{0}?postalCode={1}", apiUrl, postCode);
        ResponseEntity<AddressResponse> responseEntity = restTemplate
                .exchange(url,
                        GET,
                        httpEntity,
                        AddressResponse.class);
        AddressResponse response = responseEntity.getBody();
        if (response != null) {
            List<LocationResponse> responseList = response
                    .locations()
                    .stream()
                    .map(Location::toLocationResponse)
                    .toList();

            return ResponseDto.success(responseList, "Successfully retrieved address");
        }
        throw new ErrorResponseException(400, "Unable to retrieve address");
    }

    @NotNull
    private HttpEntity<Object> buildHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(GeneralStringConstants.API_KEY, apiKey);

        return new HttpEntity<>(headers);
    }

    /**
     * Validate post code
     *
     * @param postCode post code
     */
    private static void validatePostCode(String postCode) {
        if (postCode == null || postCode.isBlank()) {
            throw new ErrorResponseException(400, "Post code cannot be empty");
        }
        postCode = postCode.toUpperCase();

        if (postCode.length() < 5) {
            throw new ErrorResponseException(400, "Post code is too short");
        }

        if (postCode.length() > 8) {
            throw new ErrorResponseException(400, "Post code is too long");
        }

        if (!postCode.matches("[A-Z]{1,2}[0-9][A-Z0-9]?\\s?[0-9][A-Z]{2}")) {
            throw new ErrorResponseException(400, "Post code is invalid");
        }
    }

}
