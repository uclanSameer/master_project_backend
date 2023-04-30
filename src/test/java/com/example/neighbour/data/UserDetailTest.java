package com.example.neighbour.data;

import com.example.neighbour.dto.users.UserDetailDtoTest;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailTest {

    public static UserDetail getUserDetail() {
        return new UserDetail(
                UserDetailDtoTest.getUserDetailDto(),
                UserTest.getNormalUser()
        );
    }

    public static UserDetail getUserDetailForCart() {
        return new UserDetail(
                UserDetailDtoTest.getUserDetailDto(),
                null
        );
    }

    public static UserDetail getBusinessUserDetail() {
        return new UserDetail(
                UserDetailDtoTest.getUserDetailDto(),
                UserTest.getBusinessUser()
        );
    }

}