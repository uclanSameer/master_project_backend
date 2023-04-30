package com.example.neighbour.service.user;

import com.example.neighbour.data.User;
import com.example.neighbour.data.UserTest;
import com.example.neighbour.data.Verification;
import com.example.neighbour.data.VerificationTest;
import com.example.neighbour.dto.MessageDto;
import com.example.neighbour.repositories.VerificationRepository;
import com.example.neighbour.service.EmailService;
import com.example.neighbour.utils.UserUtilsTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationServiceImplTest {

    @Mock private VerificationRepository verificationRepository;
    @Mock private EmailService emailService;

    @InjectMocks private VerificationServiceImpl verificationService;

    @BeforeAll
    static void setUp() {
        UserUtilsTest.SET_NORMAL_USER_AUTHENTICATION();
    }

    @Test
    void verifySendVerificationEmailForUnverifiedUser() {
        when(verificationRepository.findByUser(any(User.class)))
                .thenReturn(
                        Optional.of(VerificationTest.getUnverifiedVerification())
                );
        doNothing().when(emailService).sendEmail(any(MessageDto.class));

        verificationService.sendUserVerificationEmailToNormalUser(UserTest.getNormalUser());

        verify(verificationRepository, times(1)).findByUser(any(User.class));

        verify(emailService, times(1)).sendEmail(any(MessageDto.class));
    }

    @Test
    void verifySendVerificationEmailForVerifiedUser(){
        when(verificationRepository.findByUser(any(User.class)))
                .thenReturn(
                        Optional.of(VerificationTest.getVerifiedVerification())
                );

        verificationService.sendUserVerificationEmailToNormalUser(UserTest.getNormalUser());

        verify(verificationRepository, times(1)).findByUser(any(User.class));

        // will return before sending email because user is already verified
        verify(emailService, times(0)).sendEmail(any(MessageDto.class));
    }

    @Test
    void verifySendVerificationEmailForBusinessUser(){
        when(verificationRepository.findByUser(any(User.class)))
                .thenReturn(
                        Optional.of(VerificationTest.getUnverifiedVerification())
                );

        verificationService.sendUserVerificationEmailToNormalUser(UserTest.getBusinessUser());

        verify(verificationRepository, times(1)).findByUser(any(User.class));

        // will return before sending email because this method is for normal users
        verify(emailService, times(0)).sendEmail(any(MessageDto.class));
    }

    @Test
    void verifySendVerificationForProperBusiness(){
        when(verificationRepository.save(any(Verification.class)))
                .thenReturn(
                        VerificationTest.getUnverifiedVerification()
                );

        doNothing().when(emailService).sendEmail(any(MessageDto.class));

        verificationService.sendVerificationEmailToBusiness(UserTest.getBusinessUser(), "setupUrl");
    }

    @Test
    void testVerifyEmail(){
        when(verificationRepository.findByToken(any(String.class)))
                .thenReturn(
                        Optional.of(VerificationTest.getUnverifiedVerification())
                );

        when(verificationRepository.save(any(Verification.class)))
                .thenReturn(
                        VerificationTest.getVerifiedVerification()
                );
        verificationService.verifyEmail(UUID.randomUUID().toString());

        verify(verificationRepository, times(1)).findByToken(any(String.class));
    }

    @Test
    void testVerifyEmailForInvalidToken(){
        when(verificationRepository.findByToken(any(String.class)))
                .thenReturn(
                        Optional.empty()
                );

        assertThrows(RuntimeException.class, () -> {
            verificationService.verifyEmail(UUID.randomUUID().toString());
        });
    }

    @Test
    void testCreateVerification(){
        when(verificationRepository.save(any(Verification.class)))
                .thenReturn(
                        VerificationTest.getUnverifiedVerification()
                );

        verificationService.createVerification(UserTest.getNormalUser());

        verify(verificationRepository, times(1)).save(any(Verification.class));
    }
}