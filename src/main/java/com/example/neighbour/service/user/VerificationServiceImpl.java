package com.example.neighbour.service.user;

import com.example.neighbour.data.User;
import com.example.neighbour.data.Verification;
import com.example.neighbour.dto.MessageDto;
import com.example.neighbour.enums.Role;
import com.example.neighbour.exception.ErrorResponseException;
import com.example.neighbour.repositories.VerificationRepository;
import com.example.neighbour.service.EmailService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;

    private final EmailService emailService;

    @Override
    public void sendUserVerificationEmail(User user) {
        Optional<Verification> verification = verificationRepository.findByUser(user);
        if (verification.isPresent() && verification.get().getIsVerified()) {
            log.info("User with email: {} is already verified", user.getEmail());
            return;
        }

        if (user.getRole() != Role.USER) {
            log.error("User with email: {} is not a user, can't verify user with role {} in this method",
                    user.getEmail(),
                    user.getRole()
            );
            return;
        }
        String token = createVerification(user);

        MessageDto messageDto = buildEmailMessage(user, token, null);

//        emailService.sendEmail(messageDto);

        log.info("Verification email has been sent to: {}", user.getEmail());
    }

    @Override
    public void sendVerificationEmailToBusiness(User user, String setupUrl) {
        createVerification(user);
        MessageDto messageDto = buildEmailMessage(user, null, setupUrl);

        emailService.sendEmail(messageDto);

        log.info("Verification email has been sent to business: {}", user.getEmail());
    }

    @Override
    public void verifyEmail(String token) {
        Verification verification = verificationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (verification.getIsVerified()) {
            log.info("User with email: {} is already verified", verification.getUser().getEmail());
            throw new ErrorResponseException(400, "User is already verified");
        }
        verification.setIsVerified(true);
        verificationRepository.save(verification);

        log.info("User with email: {} has been verified", verification.getUser().getEmail());
    }

    public String createVerification(User user) {
        Verification verification = new Verification();
        verification.setUser(user);
        String token = createUniqueToken();
        verification.setToken(token);

        verification.setIsVerified(false);

        verificationRepository.save(verification);
        return token;
    }

    /**
     * Creates unique token for verification process which is not present in db
     *
     * @return unique token
     */
    private String createUniqueToken() {
        String token = UUID.randomUUID().toString();
        //check if token exists in db
        while (verificationRepository.existsByToken(token)) {
            token = UUID.randomUUID().toString();
        }
        return token;
    }


    /**
     * Builds email message with verification link
     *
     * @param user  user to send email to
     * @param token verification token
     * @return email message
     */
    private static MessageDto buildEmailMessage(User user, String token, @Nullable String url) {
        String verificationUrl = "http://localhost:8080/api/v1/user/verify/" + token;
        verificationUrl = url != null ? url : verificationUrl;
        String htmlMessage = "<a href=\"" + verificationUrl + "\">Verify your email</a>";
        return MessageDto.builder()
                .toAddress(user.getEmail())
                .subject("Verify your email")
                .message(htmlMessage)
                .build();
    }
}
