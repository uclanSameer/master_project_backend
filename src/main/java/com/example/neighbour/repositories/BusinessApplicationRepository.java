package com.example.neighbour.repositories;

import com.example.neighbour.data.BusinessApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessApplicationRepository extends JpaRepository<BusinessApplication, Integer> {

    List<BusinessApplication> findAllByStatus(String status);

    Optional<BusinessApplication> findByUserId(Integer userId);
}