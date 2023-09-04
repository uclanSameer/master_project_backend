package com.example.neighbour.service;

import com.example.neighbour.dto.MessageDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Slf4j
@RequiredArgsConstructor
public class JavaMailService implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(MessageDto messageDto) {
        if (messageDto.isHtml()) {
            sendHtmlEmail(messageDto);
        } else {
            sendSimpleEmail(messageDto);
        }
    }

    void sendSimpleEmail(MessageDto messageDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(messageDto.to());
        message.setSubject(messageDto.subject());
        message.setText(messageDto.message());
        emailSender.send(message);
    }

    void sendHtmlEmail(MessageDto messageDto) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();

            mimeMessage.setSubject(messageDto.subject());
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage, "utf-8");
            helper.setText(messageDto.message(), true);
            helper.setTo(messageDto.to());

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {

            throw new RuntimeException(e);
        }
    }
}
