package com.example.neighbour.data;

import com.example.neighbour.dto.MenuItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cuisine")
    private String cuisine;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "nutritional_info")
    private String nutritionalInfo;

    @Column(name = "image_url")
    private String image;

    @Column(name = "is_featured")
    private boolean isFeatured;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "is_veg")
    private boolean isVeg;

    @Column(name = "instant_delivery")
    private boolean instantDelivery;

    @Column(name = "booking_required")
    private boolean bookingRequired;

    public MenuItem(MenuItemDto menuItemDto) {
        this.name = menuItemDto.name();
        this.description = menuItemDto.description();
        this.cuisine = menuItemDto.cuisine();
        this.price = menuItemDto.price();
        this.isFeatured = menuItemDto.isFeatured();
        this.nutritionalInfo = menuItemDto.nutritionalInfo();
        this.isAvailable = menuItemDto.isAvailable();
        this.isVeg = menuItemDto.isVeg();
        this.instantDelivery = menuItemDto.instantDelivery();
        this.bookingRequired = menuItemDto.bookingRequired();
    }

    public MenuItem() {

    }
}