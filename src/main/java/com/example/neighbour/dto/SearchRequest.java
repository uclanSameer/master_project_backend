package com.example.neighbour.dto;

import java.util.List;

public record SearchRequest(
        Location location, Pagination pagination, String search
) {
}

