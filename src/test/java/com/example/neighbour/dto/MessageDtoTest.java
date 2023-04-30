package com.example.neighbour.dto;

public class MessageDtoTest {
    public static MessageDto getMessageDto() {
        return MessageDto.builder()
                .to("to@to.com")
                .subject("subject")
                .message("message")
                .isHtml(true)
                .build();
    }
}