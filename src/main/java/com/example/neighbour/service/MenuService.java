package com.example.neighbour.service;

import com.example.neighbour.dto.MenuItemDto;
import com.example.neighbour.dto.ResponseDto;

import java.util.List;

public interface MenuService {
    /**
     * Adds menu item to the database
     *
     * @param menuItemDto - menu item to be added
     * @return - response dto
     */
    ResponseDto<MenuItemDto> addMenuItem(MenuItemDto menuItemDto);

    /**
     * Gets menu items from the database by cuisine
     *
     * @return - list of menu items
     */
    ResponseDto<List<MenuItemDto>> getMenusByCuisine(String cuisine);

    /**
     * Gets menu items that are featured
     *
     * @return - list of menu items
     */
    ResponseDto<List<MenuItemDto>> getFeaturedMenuItems();


    /**
     * Gets menu items from the database by business id
     *
     * @return - list of menu items
     */
    ResponseDto<List<MenuItemDto>> getMenusByBusinessId(String sellerId);


    /**
     * Gets menu items from the database by seller id
     *
     * @return - list of menu items
     */
    MenuItemDto getMenuItemById(int menuItemId);


}
