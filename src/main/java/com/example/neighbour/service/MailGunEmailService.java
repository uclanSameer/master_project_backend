package com.example.neighbour.service;

import com.example.neighbour.dto.MessageDto;
import com.example.neighbour.exception.ErrorResponseException;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.model.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailGunEmailService implements EmailService {

    @Value("${MAILGUN_DOMAIN}")
    private String domain;

    private final MailgunMessagesApi mailgunMessagesApi;

    @Override
    public void sendEmail(MessageDto messageDto) {
        try {
            Message message = Message.builder()
                    .from("noreply@neighbour.com")
                    .to(messageDto.to())
                    .subject(messageDto.subject())
                    .html(messageDto.message())
                    .build();
            mailgunMessagesApi.sendMessage(
                    domain,
                    message
            );
        } catch (Exception e) {
            log.error("Failed to send email", e);
            throw new ErrorResponseException(500, "Failed to send email");
        }
    }
}
