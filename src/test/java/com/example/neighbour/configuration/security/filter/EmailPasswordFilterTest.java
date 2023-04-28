package com.example.neighbour.configuration.security.filter;

import com.example.neighbour.configuration.security.authentication.EmailPasswordAuthentication;
import com.example.neighbour.utils.ApiConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmailPasswordFilter.class)
public class EmailPasswordFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private EmailPasswordFilter emailPasswordFilter;


    @Test
    public void shouldFilterAuthenticationRequest() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(ApiConstants.API_VERSION_1 + "user/authenticate");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain filterChain = new MockFilterChain();

        emailPasswordFilter.doFilterInternal(request, response, filterChain);

        assertFalse(emailPasswordFilter.shouldNotFilter(request));
    }


    @Test
    public void shouldNotFilterOtherRequest() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(ApiConstants.API_VERSION_1 + "any/other/request");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain filterChain = new MockFilterChain();

        emailPasswordFilter.doFilterInternal(request, response, filterChain);

        assertTrue(emailPasswordFilter.shouldNotFilter(request));
    }
}