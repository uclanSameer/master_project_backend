package com.example.neighbour.repositories;

import com.example.neighbour.data.User;
import com.example.neighbour.data.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Integer> {

    Optional<Boolean> existsByToken(String token);

    Optional<Verification> findByToken(String token);

    Optional<Verification> findByUser(User user);
}
