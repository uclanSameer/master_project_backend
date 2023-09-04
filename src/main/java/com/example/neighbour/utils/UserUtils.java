package com.example.neighbour.utils;

import com.example.neighbour.data.User;
import com.example.neighbour.dto.JwtResponseDto;
import com.example.neighbour.dto.myptv.AddressDto;
import com.example.neighbour.dto.users.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public final class UserUtils {

    private UserUtils() {
        //Utility class
        throw new IllegalStateException("Utility class");
    }

    public static User getAuthenticatedUser() {
        try {
            return (User) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (ClassCastException e) {
            log.error("Error occurred while getting the authenticated user with error message : {}", e.getMessage(), e);
            throw new ResponseStatusException(BAD_REQUEST, "Cannot get authenticated user.");
        }
    }

    public static String getProfileImageKey(User user) {
        return "image/" + user.getId() + "/profile/" + user.getId();
    }


    /**
     * Validates user details
     *
     * @param userDto - user details
     */
    public static void validateUser(UserDto userDto) {
        try {
            // validates password strength
            if (userDto.getPassword().length() < 8) {
                log.error("Password is too weak for the user {}", userDto.getEmail());
                throw new ResponseStatusException(BAD_REQUEST, "Password must be at least 8 characters");
            }

            // validates user detail
            if (userDto.getUserDetail() != null) {

                // validate phone number is valid uk number
                if (userDto.getUserDetail().getPhoneNumber().length() != 11) {
                    log.error("Phone number is invalid for the user {}", userDto.getEmail());
                    throw new ResponseStatusException(BAD_REQUEST, "Phone number must be 11 characters");
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while validating the user with email {}", userDto.getEmail(), e);
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }


    /**
     * Creates a jwt response dto
     *
     * @param userPrincipal - user principal
     * @param jwt           - jwt token
     * @return - jwt response dto
     */
    public static JwtResponseDto createJwtResponseDto(User userPrincipal, String jwt) {
        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setEmail(userPrincipal.getEmail());
        jwtResponseDto.setToken(jwt);
        jwtResponseDto.setUserId(userPrincipal.getId());
        jwtResponseDto.setAddress(new AddressDto(userPrincipal.getUserDetail().getAddress()));
        jwtResponseDto.setUserRole(userPrincipal.getRole());
        return jwtResponseDto;
    }

    public static List<UserDto> convertUserListToUserDtoList(List<User> userList) {
        return userList
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

}
