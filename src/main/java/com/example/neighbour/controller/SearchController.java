package com.example.neighbour.controller;

import com.example.neighbour.dto.MenuSearchRequest;
import com.example.neighbour.dto.Pagination;
import com.example.neighbour.dto.SellerSearchRequest;
import com.example.neighbour.utils.ApiConstants;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "search")
public class SearchController {

    private final FoodSearchService searchService;

    @PostMapping("/menu")
    public Object searchMenu(@RequestBody MenuSearchRequest search) {
        return searchService.searchMenu(search);
    }

    @PostMapping("/menu/all")
    public Object allMenu(@RequestBody Pagination search) {
        return searchService.searchMenu(new MenuSearchRequest(search, null, null, null, null));
    }

    @PostMapping("/chef")
    public Object searchBusiness(@RequestBody SellerSearchRequest search) {
        return searchService.searchBusiness(search);
    }

    @GetMapping("/chef/{id}")
    public Object findBusinessById(@PathVariable String id) {
        return searchService.findChefById(id);
    }

    @GetMapping("/cuisines")
    public Object distinctCuisines() {
        return searchService.distinctCuisines();
    }
}
