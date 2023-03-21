package com.example.neighbour.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.neighbour.data.Foodbank} entity
 */
public record FoodbankDto(String name, String address, String contactInfo, String typesOfFood) implements Serializable {
}