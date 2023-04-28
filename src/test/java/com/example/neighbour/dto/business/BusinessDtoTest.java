package com.example.neighbour.dto.business;

import com.example.neighbour.dto.users.UserDetailDto;
import com.example.neighbour.dto.users.UserDetailDtoTest;

public class BusinessDtoTest {


    public static BusinessDto getBusinessDto() {
        return new BusinessDto(
                "business@business.com",
                true,
                null,
                5D,
                UserDetailDtoTest.getUserDetailDto()
        );
    }
}