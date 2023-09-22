package com.example.neighbour.repositories;

import com.example.neighbour.data.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
