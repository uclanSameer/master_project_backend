package com.example.neighbour.controller;

import com.example.neighbour.dto.MenuSearchRequest;
import com.example.neighbour.dto.SearchRequest;
import com.example.neighbour.dto.SellerSearchRequest;
import com.example.neighbour.utils.ApiConstants;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "search")
public class SearchController {

    private final FoodSearchService searchService;

    @PostMapping("/menu")
    public Object searchMenu(@RequestBody MenuSearchRequest search) {
        log.info("Searching menu");
        return searchService.searchMenu(search);
    }

    @PostMapping("/menu/all")
    public Object allMenu(@RequestBody SearchRequest search) {
        return searchService.allMenu(search);
    }

    @PostMapping("/cheif")
    public Object searchBusiness(@RequestBody SellerSearchRequest search) {
        return searchService.searchBusiness(search);
    }

    @GetMapping("/cheif/{id}")
    public Object findBusinessById(@PathVariable String id) {
        return searchService.findChefById(id);
    }

    @GetMapping("/cuisines")
    public Object distinctCuisines() {
        return searchService.distinctCuisines();
    }
}
