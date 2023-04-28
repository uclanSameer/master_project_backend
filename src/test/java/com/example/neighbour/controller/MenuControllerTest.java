package com.example.neighbour.controller;

import com.example.neighbour.dto.MenuItemDtoTest;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    @Test
    void testCreateMenu() {
        var menuItemDto = MenuItemDtoTest.getMenuItemDto();

        when(menuService.addMenuItem(menuItemDto)).thenReturn(
                ResponseDto.success(
                        menuItemDto,
                        "Menu item created successfully"
                )
        );

        var response = menuController.createMenu(menuItemDto);

        assertEquals(menuItemDto, response.getData());
    }

    @Test
    void testGetMenusByCuisine() {
        var menuItemDto = MenuItemDtoTest.getMenuItemDto();

        when(menuService.getMenusByCuisine("cuisine")).thenReturn(
                ResponseDto.success(
                        List.of(menuItemDto),
                        "Menu item created successfully"
                )
        );

        var response = menuController.getMenusByCuisine("cuisine");

        assertEquals(1, response.getData().size());
    }

    @Test
    void testGetFeaturedMenus() {
        var menuItemDto = MenuItemDtoTest.getMenuItemDto();

        when(menuService.getFeaturedMenuItems()).thenReturn(
                ResponseDto.success(
                        List.of(menuItemDto),
                        "Menu item created successfully"
                )
        );

        var response = menuController.getFeaturedMenus();

        assertEquals(1, response.getData().size());
    }
}