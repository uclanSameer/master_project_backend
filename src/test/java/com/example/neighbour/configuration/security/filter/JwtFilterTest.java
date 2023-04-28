package com.example.neighbour.configuration.security.filter;

import com.example.neighbour.utils.ApiConstants;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.ProviderManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(JwtFilter.class)
class JwtFilterTest {

    @Mock
    private ProviderManager providerManager;


    @InjectMocks
    private JwtFilter jwtFilter;


    @Test
    void shouldFilterOutSomeRequest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(ApiConstants.API_VERSION_1 + "webhook");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain filterChain = new MockFilterChain();

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertTrue(jwtFilter.shouldNotFilter(request));
    }

    @Test
    void shouldNotFilterOutSomeRequest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(ApiConstants.API_VERSION_1 + "request/that/shoud/be/authenticated");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain filterChain = new MockFilterChain();

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertFalse(jwtFilter.shouldNotFilter(request));
    }


}