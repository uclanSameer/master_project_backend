package com.example.neighbour.data;

import com.example.neighbour.dto.users.UserDetailDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "user_details")
@Getter
public class UserDetail implements Serializable {
    @Id
    @Column(name = "user_details_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne(mappedBy = "userDetail", cascade = CascadeType.ALL)
    @Setter
    private Address address;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Setter
    @Column(name = "image_url")
    private String imageUrl;



    public UserDetail(UserDetailDto userDetailDto, User user) {
        this.user = user;
        this.name = userDetailDto.getName();
        this.phoneNumber = userDetailDto.getPhoneNumber();
        this.imageUrl = userDetailDto.getImageUrl();
        this.address = new Address(userDetailDto.getAddress(), userDetailDto.getPosition());
    }

    public UserDetail() {

    }
}