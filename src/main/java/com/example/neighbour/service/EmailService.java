package com.example.neighbour.service;

import com.example.neighbour.dto.MessageDto;

public interface EmailService {

    void sendEmail(MessageDto messageDto);

}
