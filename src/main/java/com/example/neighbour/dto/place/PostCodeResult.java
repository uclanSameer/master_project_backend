package com.example.neighbour.dto.place;

public record PostCodeResult (
        String postcode,
        String admin_district,
        String admin_ward,
        String admin_county,
        String state,
        String country,
        double latitude,
        double longitude
) {
}
