package com.example.neighbour.repositories;

import com.example.neighbour.data.User;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    void save(UserDto user);

    List<User> findAllByRoleNot(Role role);
    List<User> findAllByRole(Role role);
}