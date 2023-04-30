package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.dto.users.UserDtoTest;
import com.example.neighbour.enums.Role;
import com.example.neighbour.service.UserService;
import com.example.neighbour.service.user.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsControllerTest {

    @Mock
    ProfileService profileService;
    @Mock
    UserService userService;

    @InjectMocks
    UserDetailsController userDetailsController;

    @Test
    void getProfile() {
        UserDto userDto = UserDtoTest.getUserDto();
        when(profileService.getProfile()).thenReturn(ResponseDto.success(userDto, "success"));

        ResponseDto<UserDto> responseDto = userDetailsController.getProfile();

        assertEquals(userDto, responseDto.getData());
    }

    @Test
    void updateProfilePicture() {
        when(profileService.uploadProfilePicture("image")).thenReturn(ResponseDto.success("image", "success"));

        ResponseDto<String> responseDto = userDetailsController.updateProfilePicture(
                new com.example.neighbour.dto.UploadImage("image")
        );

        assertEquals("image", responseDto.getData());
    }

    @Test
    void getAllUsers() {
        when(userService.getAllUsers()).thenReturn(ResponseDto.success(UserDtoTest.getUserDtoList(), "success"));

        ResponseDto<java.util.List<UserDto>> responseDto = userDetailsController.getAllUsers();


        assertEquals("success", responseDto.getMessage());

        assertEquals(1, responseDto.getData().size());
    }

    @Test
    void getAllUsersByRole() {
        when(userService.getAllUsersByRole(Role.ADMIN))
                .thenReturn(ResponseDto.success(UserDtoTest.getUserDtoList(), "success"));

        ResponseDto<List<UserDto>> responseDto = userDetailsController.getAllUsersByRole(Role.ADMIN);


        assertEquals("success", responseDto.getMessage());

        assertEquals("test@test.com", responseDto.getData().get(0).getEmail());
    }
}

