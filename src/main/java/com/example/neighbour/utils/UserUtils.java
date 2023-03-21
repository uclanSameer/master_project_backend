package com.example.neighbour.utils;

import com.example.neighbour.data.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

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

    public static String getProfileImageKey() {
        User user = getAuthenticatedUser();
        return  "image/" + user.getId() + "/profile/" + user.getId();
    }

    public static String getProfileImageKey(User user) {
        return  "image/" + user.getId() + "/profile/" + user.getId();
    }
}
