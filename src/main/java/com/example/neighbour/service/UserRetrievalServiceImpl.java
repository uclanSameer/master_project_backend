package com.example.neighbour.service;

import com.example.neighbour.data.User;
import com.example.neighbour.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserRetrievalServiceImpl implements UserRetrievalService {

    private final UserRepository  userRepository;
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
