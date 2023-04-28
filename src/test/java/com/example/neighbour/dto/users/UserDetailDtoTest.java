package com.example.neighbour.dto.users;

import com.example.neighbour.dto.myptv.AddressDto;
import com.example.neighbour.dto.myptv.Position;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailDtoTest {

    public static UserDetailDto getUserDetailDto() {
        return new UserDetailDto(
                "test",
                "1234567890",
                "test.com",
                getAddressDto(),
                getPosition()
        );
    }

    private static Position getPosition() {
        return null;
    }

    private static AddressDto getAddressDto() {
        return null;
    }

}