package com.example.neighbour.dto;

import com.example.neighbour.dto.users.UserDto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.example.neighbour.data.Donation} entity
 */
public record DonationDto(Instant dateTime, UserDto donor, FoodbankDto foodBank, String items) implements Serializable {
}