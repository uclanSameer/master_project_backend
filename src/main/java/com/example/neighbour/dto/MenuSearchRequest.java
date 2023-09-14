package com.example.neighbour.dto;

public record MenuSearchRequest(Location location, Pagination pagination, String search, String email) {
}
