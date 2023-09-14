package com.example.neighbour.controller;

import com.example.neighbour.dto.MenuSearchRequest;
import com.example.neighbour.dto.SearchRequest;
import com.example.neighbour.dto.BusinessSearchRequest;
import com.example.neighbour.dto.SellerSearchRequest;
import org.springframework.web.bind.annotation.*;

//@RestController("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/menu")
    public Object searchMenu(@RequestBody MenuSearchRequest search) {
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
