package com.example.neighbour.dto.myptv;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    public static Location getLocation() {
        var location = new Location();
        location.setReferencePosition(PositionTest.getPosition());
        location.setRoadAccessPosition(PositionTest.getPosition());
        location.setAddress(AddressDtoTest.getAddressDto());
        location.setFormattedAddress("formattedAddress");
        location.setLocationType("locationType");
        location.setQuality(new Quality());
        return location;
    }

}