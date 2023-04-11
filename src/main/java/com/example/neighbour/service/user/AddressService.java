package com.example.neighbour.service.user;

import com.example.neighbour.data.User;
import com.example.neighbour.data.UserDetail;
import com.example.neighbour.dto.users.UserDto;

public interface AddressService {

    /**
     * Saves address to database
     *
     * @param userDto     - user dto
     * @param savedUser   - saved user
     * @param savedUserDetails - saved user details
     */
    void saveAddress(UserDto userDto, User savedUser, UserDetail savedUserDetails);
}
