package com.example.neighbour.repositories;

import com.example.neighbour.data.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface MenuRepository extends JpaRepository<MenuItem, Integer> {

    Stream<MenuItem> findAllByCuisine(String cuisine);
    Optional<List<MenuItem>> findAllByBusinessId(int business);

    Stream<MenuItem> findAllByIsFeatured(boolean isFeatured);
}