package com.example.neighbour.controller;

import com.example.neighbour.dto.JwtResponseDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.dto.users.UserDtoTest;
import com.example.neighbour.service.UserService;
import com.example.neighbour.service.user.VerificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private VerificationService verificationService;

    @InjectMocks
    private UserController userController;

    @Test
    void registerUser() {
        UserDto userDto = UserDtoTest.getUserDto();

        when(userService.registerUser(userDto)).thenReturn(ResponseDto.success(userDto, "User registered successfully"));

        ResponseDto<UserDto> responseDto = userController.registerUser(userDto);

        assert responseDto.getData().getEmail().equals(userDto.getEmail());

        assertEquals(responseDto.getMessage(), "User registered successfully");
    }

    @Test
    void registerBusinessUser() {
        UserDto userDto = UserDtoTest.getUserDto();

        when(userService.registerBusiness(userDto)).thenReturn(ResponseDto.success(null, "User registered successfully"));

        ResponseDto<String> responseDto = userController.registerBusinessUser(userDto);

        assertEquals(responseDto.getMessage(), "User registered successfully");
    }

    @Test
    void verifyToken(){
        doNothing().when(verificationService).verifyEmail("token");

        ResponseDto<String> responseDto = userController.verifyEmail("token");

        assertEquals(responseDto.getMessage(), "Email verified successfully");
    }

    @Test
    void authenticate(){

        when(userService.generateJwtToken()).thenReturn(ResponseDto.success(null, "Authenticated successfully"));
        ResponseDto<JwtResponseDto> responseDto = userController.authenticate();

        assertEquals(responseDto.getMessage(), "Authenticated successfully");
    }

}