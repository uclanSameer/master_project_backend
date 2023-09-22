package com.example.neighbour.service.aws;

import com.example.neighbour.dto.MessageDto;
import com.example.neighbour.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

@Service
@RequiredArgsConstructor
public class SesService implements EmailService {

    @Value("${aws.ses.sender}")
    private String sender;
    private final SesV2Client emailClient;

    @Override
    public void sendEmail(MessageDto messageDto) {
        Destination destination = Destination.builder()
                .toAddresses("kazekage556@gmail.com")
                .build();

        Content subjectContent = Content.builder()
                .data(messageDto.subject())
                .build();

        Content bodyContent = Content.builder()
                .data(messageDto.message())
                .build();

        Body body = getBody(bodyContent,  messageDto.isHtml());

        Message emailMessage = Message.builder()
                .subject(subjectContent)
                .body(body)
                .build();

        SendEmailRequest request = SendEmailRequest.builder()
                .destination(destination)
                .fromEmailAddress(sender)
                .content(builder -> builder.simple(emailMessage))
                .build();

        emailClient.sendEmail(request);


    }

    private static Body getBody(Content bodyContent, boolean isHtml) {
        if (isHtml) {
            return Body.builder()
                    .html(bodyContent)
                    .build();
        }
        return Body.builder()
                .text(bodyContent)
                .build();
    }

}
