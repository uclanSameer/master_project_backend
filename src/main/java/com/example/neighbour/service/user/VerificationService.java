package com.example.neighbour.service.user;

import com.example.neighbour.data.User;

public interface VerificationService {

    /**
     * Sends verification email to user
     *
     * @param user - user to send email to
     */
    void sendUserVerificationEmail(User user);


    /**
     * Sends verification email to business
     *
     * @param user - user to send email to
     * @param setupUrl - url to set up business
     */
    void sendVerificationEmailToBusiness(User user, String setupUrl);


    /**
     * Verifies user email
     *
     * @param token - verification token
     */
    void verifyEmail(String token);


    /**
     * Creates verification token for user
     *
     * @param user - user to create verification token for
     * @return - verification token
     */
    String createVerification(User user);
}
