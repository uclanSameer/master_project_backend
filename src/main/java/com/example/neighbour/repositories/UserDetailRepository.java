package com.example.neighbour.repositories;

import com.example.neighbour.data.UserDetail;
import com.example.neighbour.dto.UserDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {

    void save(UserDetailDto user);
}