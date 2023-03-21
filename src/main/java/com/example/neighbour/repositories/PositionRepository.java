package com.example.neighbour.repositories;

import com.example.neighbour.data.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<PositionEntity, Integer> {
}
