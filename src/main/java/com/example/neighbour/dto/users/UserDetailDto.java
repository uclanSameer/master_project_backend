package com.example.neighbour.dto.users;

import com.example.neighbour.data.UserDetail;
import com.example.neighbour.dto.myptv.AddressDto;
import com.example.neighbour.dto.myptv.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link UserDetail} entity
 */
@Data
@Builder
@AllArgsConstructor
public class UserDetailDto implements Serializable {
    private final String name;
    private final String phoneNumber;

    private String imageUrl;

    private AddressDto address;

    private Position position;


    public UserDetailDto(UserDetail userDetail) {
        this.name = userDetail.getName();
        this.phoneNumber = userDetail.getPhoneNumber();
        this.imageUrl = userDetail.getImageUrl();
        this.address = new AddressDto(userDetail.getAddress());
        this.position = new Position(userDetail.getAddress().getPosition());
    }

    public UserDetailDto() {
        this.name = null;
        this.phoneNumber = null;
        this.imageUrl = null;
    }
}