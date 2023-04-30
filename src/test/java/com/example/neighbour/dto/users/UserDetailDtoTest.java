package com.example.neighbour.dto.users;

import com.example.neighbour.dto.myptv.AddressDto;
import com.example.neighbour.dto.myptv.AddressDtoTest;
import com.example.neighbour.dto.myptv.Position;
import com.example.neighbour.dto.myptv.PositionTest;

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
        return PositionTest.getPosition();
    }

    private static AddressDto getAddressDto() {
        return AddressDtoTest.getAddressDto();
    }

}