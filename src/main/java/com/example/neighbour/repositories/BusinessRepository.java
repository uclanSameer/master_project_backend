package com.example.neighbour.repositories;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Integer> {

    Optional<Business> findByUser(User user);

    Optional<Business> findByAccountId(String accountId);
}