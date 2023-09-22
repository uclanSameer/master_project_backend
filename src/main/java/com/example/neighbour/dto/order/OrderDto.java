package com.example.neighbour.dto.order;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.example.neighbour.data.Order} entity
 */
public record OrderDto(String status, LocalDate date) implements Serializable {
}