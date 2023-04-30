package com.example.neighbour.data;

import com.example.neighbour.dto.users.UserDtoTest;
import com.example.neighbour.enums.Role;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {


    public static User getNormalUser() {
        User user = new User(
                UserDtoTest.getUserDto(),
                Role.USER
        );
        user.setUserDetail(UserDetailTest.getUserDetailForCart());
        user.setId(1);
        return user;
    }

    public static User getAdminUser() {
        return new User(
                UserDtoTest.getUserDto(),
                Role.ADMIN
        );
    }




    public static User getBusinessUser() {
        return new User(
                UserDtoTest.getUserDto(),
                Role.BUSINESS
        );
    }

    public static User getDeliveryUser() {
        return new User(
                UserDtoTest.getUserDto(),
                Role.DELIVERY
        );
    }



}