package com.example.neighbour.controller;


import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.UploadImage;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.enums.Role;
import com.example.neighbour.service.UserService;
import com.example.neighbour.service.user.ProfileService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "details")
public class UserDetailsController {

    private final ProfileService profileService;

    private final UserService  userService;


    @PostMapping("/profile")
    public ResponseDto<UserDto> getProfile() {
        return profileService.getProfile();
    }

    @PostMapping("/update/picture")
    public ResponseDto<String> updateProfilePicture(@RequestBody UploadImage uploadImage) {
        return profileService.uploadProfilePicture(uploadImage.image());
    }

    @GetMapping("/users/all")
    public ResponseDto<List<UserDto>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/role")
    public ResponseDto<List<UserDto>> getAllUsersByRole(@Param("role") Role role) {
        return userService.getAllUsersByRole(role);
    }
}
