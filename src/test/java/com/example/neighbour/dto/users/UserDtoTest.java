package com.example.neighbour.dto.users;

import java.util.List;

public class UserDtoTest {

    public static UserDto getUserDto() {
        return new UserDto(
                "test@test.com",
                "test",
                UserDetailDtoTest.getUserDetailDto()
        );

    }

    public static List<UserDto> getUserDtoList() {
        return List.of(getUserDto());
    }
}