package com.example.neighbour.dto.place;


//TODO: Merge this with PostCodeResult
public record PostCodeData(
        String code,

//        @JsonProperty("admin_district")
        String city,
        String address,
        String state,
        String country,
        double latitude,
        double longitude
) {
}
