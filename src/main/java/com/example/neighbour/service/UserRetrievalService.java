package com.example.neighbour.service;

import com.example.neighbour.data.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserRetrievalService {

    User getUserByEmail(String email);
}
