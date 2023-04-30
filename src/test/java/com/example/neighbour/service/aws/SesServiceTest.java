package com.example.neighbour.service.aws;

import com.example.neighbour.dto.MessageDto;
import com.example.neighbour.dto.MessageDtoTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SendEmailResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SesServiceTest {

    @Mock
    private SesV2Client emailClient;

    @InjectMocks
    private SesService sesService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(sesService, "sender", "testSender@test.com");
    }

    @Test
    void sendEmail() {
        SendEmailResponse sendEmailResponse = mock(SendEmailResponse.class);
        when(emailClient.sendEmail(any(SendEmailRequest.class))).thenReturn(sendEmailResponse);
        MessageDto messageDto = MessageDtoTest.getMessageDto();
        sesService.sendEmail(messageDto);
        verify(emailClient, times(1)).sendEmail(any(SendEmailRequest.class));
    }
}