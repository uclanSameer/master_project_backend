package com.example.neighbour.data.cart;

import com.example.neighbour.data.User;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.dto.cart.CartDto;
import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user=" + user +
                '}';
    }

    public CartDto toDto() {
        return new CartDto(
                new UserDto(user)
        );
    }
}