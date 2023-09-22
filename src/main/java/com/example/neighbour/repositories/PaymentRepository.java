package com.example.neighbour.repositories;

import com.example.neighbour.data.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}