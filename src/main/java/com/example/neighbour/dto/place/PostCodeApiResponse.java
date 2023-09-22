package com.example.neighbour.dto.place;

import java.util.List;

public record PostCodeApiResponse(
        String status,
        List<PostCodeResult> result
) {
}
