package com.example.neighbour.data;

import com.example.neighbour.dto.myptv.AddressDtoTest;
import com.example.neighbour.dto.myptv.PositionTest;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    public static Address getAddress(){
        return new Address(
                AddressDtoTest.getAddressDto(),
                PositionTest.getPosition()
        );
    }

}