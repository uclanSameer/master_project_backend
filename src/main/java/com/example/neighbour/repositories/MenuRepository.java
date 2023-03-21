package com.example.neighbour.repositories;

import com.example.neighbour.data.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface MenuRepository extends JpaRepository<MenuItem, Integer> {

    Stream<MenuItem> findAllByCuisine(String cuisine);
    Stream<MenuItem> findAllByBusiness(String business);

    Stream<MenuItem> findAllByIsFeatured(boolean isFeatured);
}