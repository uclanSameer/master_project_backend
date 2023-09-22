package com.example.neighbour.service.user;

import com.example.neighbour.data.User;
import com.example.neighbour.data.UserDetail;
import com.example.neighbour.dto.users.UserDto;

/**
 * This interface defines the methods to be implemented by the AddressService class.
 */
public interface AddressService {

    /**
     * Saves the address of a user to the database.
     *
     * @param userDto           the user dto containing the address information
     * @param savedUser         the saved user object
     * @param savedUserDetails  the saved user details object
     */
    void saveAddress(UserDto userDto, User savedUser, UserDetail savedUserDetails);
}
