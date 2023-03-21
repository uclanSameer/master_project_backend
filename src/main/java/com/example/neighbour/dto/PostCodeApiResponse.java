package com.example.neighbour.dto;

import java.util.List;

public record PostCodeApiResponse(
        String status,
        List<PostCodeResult> result
) {
}
