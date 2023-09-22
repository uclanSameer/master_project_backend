package com.example.neighbour.controller;

import com.example.neighbour.dto.JwtResponseDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.enums.Role;
import com.example.neighbour.service.UserService;
import com.example.neighbour.service.user.VerificationService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(ApiConstants.API_VERSION_1 + "user")
public class UserController {

    private final UserService userService;

    private final VerificationService verificationService;

    @PostMapping("/register")
    public ResponseDto<UserDto> registerUser(@RequestBody UserDto userDto) {
        log.info("Registering user: {}", userDto.getEmail());
        return userService.registerUser(userDto);
    }

    @PostMapping("/register/business")
    public ResponseDto<String> registerBusinessUser(@RequestBody UserDto userDto) {
        log.info("Registering business user: {}", userDto.getEmail());
        return userService.registerBusiness(userDto);
    }

    @GetMapping("/verify/{token}")
    public ResponseDto<String> verifyEmail(@PathVariable String token) {
        log.info("Verifying email with token: {}", token);
        verificationService.verifyEmail(token);
        return ResponseDto.success(null, "Email verified successfully");
    }



    @PostMapping("/authenticate")
    public ResponseDto<JwtResponseDto> authenticate() {
        return userService.generateJwtToken();
    }
}
