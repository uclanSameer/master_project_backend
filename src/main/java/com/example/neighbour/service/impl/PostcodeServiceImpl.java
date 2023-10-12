package com.example.neighbour.service.impl;

import com.example.neighbour.dto.place.PostCodeApiResponse;
import com.example.neighbour.dto.place.PostCodeData;
import com.example.neighbour.dto.place.PostCodeResult;
import com.example.neighbour.service.PostcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostcodeServiceImpl implements PostcodeService {
    private final RestTemplate restTemplate;
    @Value("${postcode.api.url}")
    String postcodeApiUrl;

    @Override
    @RegisterReflectionForBinding({PostCodeApiResponse.class})
    public PostCodeData getPostCode(String code) {

        String postcodeApiUrl1 = postcodeApiUrl + "?q=" + code;
        PostCodeApiResponse response = restTemplate.getForObject(postcodeApiUrl1, PostCodeApiResponse.class);
        PostCodeResult postCodeResult;
        if (response != null) {
            postCodeResult = response.result().get(0);
            return new PostCodeData(postCodeResult.postcode(),
                    postCodeResult.admin_district(),
                    postCodeResult.admin_ward(),
                    postCodeResult.admin_county(),
                    postCodeResult.country(),
                    postCodeResult.latitude(),
                    postCodeResult.longitude()
            );
        }
        throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Postcode not found");
    }
}
