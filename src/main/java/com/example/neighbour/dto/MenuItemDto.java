package com.example.neighbour.dto;

import com.example.neighbour.data.MenuItem;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link MenuItem} entity
 */
public record MenuItemDto(
        @Nullable
        int id,
        String name,
        String description,
        String cuisine,
        BigDecimal price,
        String nutritionalInfo,

        String image,
        boolean isFeatured,
        @Nullable
        Integer businessId,
        boolean isAvailable,
        boolean isVeg,
        boolean instantDelivery,
        boolean bookingRequired,
        @Nullable
        String businessEmail
) implements Serializable {

    public MenuItemDto(MenuItem menuItem) {
        this(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getCuisine(),
                menuItem.getPrice(),
                menuItem.getNutritionalInfo(),
                menuItem.getImage(),
                menuItem.isFeatured(),
                menuItem.getBusiness().getId(),
                menuItem.isAvailable(),
                menuItem.isVeg(),
                menuItem.isInstantDelivery(),
                menuItem.isBookingRequired(),
                menuItem.getBusiness().getUser().getEmail()
        );
    }

    public MenuItemDto(MenuItem menuItem, String image) {
        this(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getCuisine(),
                menuItem.getPrice(),
                menuItem.getNutritionalInfo(),
                image,
                menuItem.isFeatured(),
                menuItem.getBusiness().getId(),
                menuItem.isAvailable(),
                menuItem.isVeg(),
                menuItem.isInstantDelivery(),
                menuItem.isBookingRequired(),
                menuItem.getBusiness().getUser().getEmail()
        );
    }

    public MenuItemDto(MenuItemDto menuItemDto, String image) {
        this(
                menuItemDto.id(),
                menuItemDto.name(),
                menuItemDto.description(),
                menuItemDto.cuisine(),
                menuItemDto.price(),
                menuItemDto.nutritionalInfo(),
                image,
                menuItemDto.isFeatured(),
                menuItemDto.businessId(),
                menuItemDto.isAvailable(),
                menuItemDto.isVeg(),
                menuItemDto.instantDelivery(),
                menuItemDto.bookingRequired(),
                menuItemDto.businessEmail());
    }

}