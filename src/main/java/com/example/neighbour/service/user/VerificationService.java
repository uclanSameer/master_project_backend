package com.example.neighbour.service.user;

import com.example.neighbour.data.User;

public interface VerificationService {

    /**
     * Sends verification email to user
     *
     * @param user - user to send email to
     */
    void sendVerificationEmail(User user);

    void sendVerificationEmailToBusiness(User user, String setupUrl);


    /**
     * Verifies user email
     *
     * @param token - verification token
     */
    void verifyEmail(String token);
}
