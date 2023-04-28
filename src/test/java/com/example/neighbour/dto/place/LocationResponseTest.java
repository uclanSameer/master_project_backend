package com.example.neighbour.dto.place;

import com.example.neighbour.dto.myptv.AddressDtoTest;
import com.example.neighbour.dto.myptv.PositionTest;

public class LocationResponseTest {

    public static LocationResponse getLocationResponse() {
        return new LocationResponse(
                PositionTest.getPosition(),
                AddressDtoTest.getAddressDto(),
                "test"
        );
    }

}