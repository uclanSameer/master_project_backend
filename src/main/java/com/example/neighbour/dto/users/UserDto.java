package com.example.neighbour.dto.users;

import com.example.neighbour.data.User;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
@Builder
public class UserDto implements Serializable {
    private final String email;
    private String password;

    @Nullable
    private UserDetailDto userDetail;

    public UserDto() {
        this.email = null;
        this.userDetail = null;
    }

    public UserDto(String email, String password, UserDetailDto userDetail) {
        this.email = email;
        this.password = password;
        this.userDetail = userDetail;
    }

    public UserDto(User user) {
        this.email = user.getEmail();
        this.password = "*********";
        this.userDetail = user.getUserDetail() == null ?
                null :
                new UserDetailDto(user.getUserDetail());
    }



    public void setPassword(String password) {
        this.password = password;
    }
}