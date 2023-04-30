package com.example.neighbour.dto.myptv;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddressResponseTest {

    public static AddressResponse getAddressResponse() {
        return new AddressResponse(
                List.of(LocationTest.getLocation())
        );
    }

}