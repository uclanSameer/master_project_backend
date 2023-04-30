package com.example.neighbour.service.impl;

import com.example.neighbour.data.BusinessTest;
import com.example.neighbour.dto.StripeAccountResponse;
import com.example.neighbour.dto.business.EsBusinessDto;
import com.example.neighbour.repositories.BusinessRepository;
import com.example.neighbour.service.ElasticSearchService;
import com.example.neighbour.service.StripeService;
import com.example.neighbour.utils.UserUtilsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessServiceImplTest {

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private ElasticSearchService<EsBusinessDto> esService;

    @Mock
    private StripeService stripeService;

    @InjectMocks
    private BusinessServiceImpl businessService;

    @Test
    void verifyAddBusiness() {
        var business = BusinessTest.createBusiness();

        when(businessRepository.save(business)).thenReturn(business);

        when(stripeService.registerBusinessAccount(business.getUser().getEmail())).thenReturn(
                new StripeAccountResponse("url", "accountId")
        );

        doNothing().when(esService).addDocument(any(), any(), any());

        var result = businessService.addBusiness(business);

        assertEquals("url", result);

        verify(businessRepository, times(1)).save(business);

        verify(stripeService, times(1)).registerBusinessAccount(business.getUser().getEmail());

        verify(esService, times(1)).addDocument(any(), any(), any());
    }

    @Test
    void verifyGetCurrentBusiness() {
        var business = BusinessTest.createBusiness();

        UserUtilsTest.SET_BUSINESS_USER_AUTHENTICATION();

        when(businessRepository.findByUser(any())).thenReturn(Optional.of(business));

        var result = businessService.getCurrentBusiness();

        assertEquals(business, result);

        verify(businessRepository, times(1)).findByUser(any());
    }

    @Test
    void verifyGetBusinessByAccountId() {
        var business = BusinessTest.createBusiness();

        when(businessRepository.findByAccountId(any())).thenReturn(Optional.of(business));

        var result = businessService.getBusinessByAccountId("accountId");

        assertEquals(business, result);

        verify(businessRepository, times(1)).findByAccountId(any());
    }


}