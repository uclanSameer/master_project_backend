package com.example.neighbour.service;

import com.example.neighbour.data.User;
import com.example.neighbour.dto.JwtResponseDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.UserDto;
import com.example.neighbour.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * Registers user in the database
     *
     * @param userDto - user to be registered
     * @return - response dto
     */
    ResponseDto<UserDto> registerUser(UserDto userDto);


    /**
     * Registers user with the given role
     *
     * @param userDto - user to be registered
     * @param role    - role of the user
     * @return - user dto
     */
    User registerUserWithRole(UserDto userDto, Role role);

    /**
     * Generates JWT token for the user
     *
     * @return - jwt response dto
     */
    ResponseDto<JwtResponseDto> generateJwtToken();


    /**
     * Updates the role of the user
     *
     * @param email - email of the user
     * @param role  - role to be updated
     * @return - updated user
     */
    User updateRole(String email, Role role);

    /**
     * Gets the profile of the user
     *
     * @return - user dto
     */
    ResponseDto<UserDto> getProfile();

    /**
     * Updates the profile of the user
     *
     * @param userDto - user dto
     * @return - user dto
     */
    ResponseDto<String> registerBusiness(UserDto userDto);

    /**
     * Updates the profile picture of the user
     *
     * @param url
     * @return
     */
    ResponseDto<String> uploadProfilePicture(String url);

    /**
     * Checks if any user is present in the database
     *
     * @return - true if no users are present
     */
    boolean hasNoUser();


    void removeAllExceptAdmin();

    void updateBusinessAccountAsVerified(String accountId);
}
