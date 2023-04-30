package com.example.neighbour.data;

import com.example.neighbour.dto.MenuItemDtoTest;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemTest {

    public static MenuItem createMenuItem() {
        MenuItem menuItem = new MenuItem(MenuItemDtoTest.getMenuItemDto());
        menuItem.setBusiness(BusinessTest.createBusiness());
        return menuItem;
    }
}