package com.example.neighbour.controller;


import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.UploadImage;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.service.user.ProfileService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "details")
public class UserDetailsController {

    private final ProfileService profileService;


    @PostMapping("/profile")
    public ResponseDto<UserDto> getProfile() {
        return profileService.getProfile();
    }

    @PostMapping("/update/picture")
    public ResponseDto<String> updateProfilePicture(@RequestBody UploadImage uploadImage) {
        return profileService.uploadProfilePicture(uploadImage.image());
    }
}
