package com.example.neighbour.repositories;

import com.example.neighbour.data.User;
import com.example.neighbour.dto.UserDto;
import com.example.neighbour.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    void save(UserDto user);

    Iterable<User> findAllByRoleNot(Role role);
}