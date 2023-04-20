package com.example.neighbour.configuration.security.filter;

import com.example.neighbour.configuration.security.authentication.EmailPasswordAuthentication;
import com.example.neighbour.utils.ApiConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        when(authenticationManager.authenticate(any()))
                .thenReturn(new EmailPasswordAuthentication("email", "password"));
    }

    @org.junit.jupiter.api.Test
    public void shouldNotFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ApiConstants.API_VERSION_1 + "/address"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}