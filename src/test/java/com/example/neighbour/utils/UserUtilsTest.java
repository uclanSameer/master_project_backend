package com.example.neighbour.utils;

import com.example.neighbour.configuration.security.authentication.EmailPasswordAuthentication;
import com.example.neighbour.data.User;
import com.example.neighbour.data.UserDetailTest;
import com.example.neighbour.data.UserTest;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtilsTest {

    public static void SET_NORMAL_USER_AUTHENTICATION() {
        User user = UserTest.getNormalUser();
        user.setUserDetail(UserDetailTest.getUserDetail());
        String password = "password";
        SecurityContextHolder.getContext().setAuthentication(
                new EmailPasswordAuthentication(user, password, user.getAuthorities())
        );
    }

    public static void SET_BUSINESS_USER_AUTHENTICATION() {
        User user = UserTest.getBusinessUser();
        user.setUserDetail(UserDetailTest.getBusinessUserDetail());
        String password = "password";
        SecurityContextHolder.getContext().setAuthentication(
                new EmailPasswordAuthentication(user, password, user.getAuthorities())
        );
    }

}