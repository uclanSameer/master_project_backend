package com.example.neighbour.dto;

import com.example.neighbour.dto.myptv.AddressDto;
import com.example.neighbour.dto.myptv.Position;

public record LocationResponse(
        Position referencePosition,
        AddressDto address,
        String formattedAddress
) {
}
