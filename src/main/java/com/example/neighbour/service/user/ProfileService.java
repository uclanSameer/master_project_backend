package com.example.neighbour.service.user;

import com.example.neighbour.data.User;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.users.UserDto;

public interface ProfileService {

    /**
     * Uploads profile picture to S3 bucket
     *
     * @param url - url of the image
     * @return - response dto
     */
    ResponseDto<String> uploadProfilePicture(String url);

    ResponseDto<String> uploadProfilePicture(String url, User user);

    /**
     * Gets the profile of the user
     *
     * @return - user dto
     */
    ResponseDto<UserDto> getProfile();
}
