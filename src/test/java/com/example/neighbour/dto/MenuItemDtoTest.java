package com.example.neighbour.dto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemDtoTest {

    public static MenuItemDto getMenuItemDto() {
        return new MenuItemDto(
                1,
                "name",
                "description",
                "image",
                BigDecimal.TEN,
                "100 calories",
                "https://image.com/image.jpg",
                true,
                1,
                true,
                false,
                true,
                false,
                "email"
        );
    }
}