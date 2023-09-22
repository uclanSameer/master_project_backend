package com.example.neighbour.dto;

import com.example.neighbour.dto.myptv.AddressDto;
import com.example.neighbour.enums.Role;
import lombok.Data;

@Data
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private String email;
    private Role userRole;

    private AddressDto address;
}
