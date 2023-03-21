package com.example.neighbour.controller;

import com.example.neighbour.dto.MenuItemDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.service.MenuService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/create")
    public ResponseDto<MenuItemDto> createMenu(@RequestBody MenuItemDto menu) {
        log.info("Creating menu item");
        return menuService.addMenuItem(menu);
    }

    @GetMapping("/{cuisine}")
    public ResponseDto<List<MenuItemDto>> getMenusByCuisine(@PathVariable String cuisine) {
        log.info("Getting menus by cuisine: {}", cuisine);
        return menuService.getMenusByCuisine(cuisine);
    }

    @GetMapping("/featured")
    public ResponseDto<List<MenuItemDto>> getFeaturedMenus() {
        log.info("Getting featured menus");
        return menuService.getFeaturedMenuItems();
    }
}
