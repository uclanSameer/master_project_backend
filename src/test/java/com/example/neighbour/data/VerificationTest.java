package com.example.neighbour.data;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationTest {

    public static Verification getUnverifiedVerification() {
        return new Verification(
                1,
                UserTest.getNormalUser(),
                "1234567890",
                false
        );
    }

    public static Verification getVerifiedVerification() {
        return new Verification(
                1,
                UserTest.getNormalUser(),
                "1234567890",
                true
        );
    }

}