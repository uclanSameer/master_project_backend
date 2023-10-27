package com.example.neighbour.dto;

import java.util.List;

public record MenuSearchRequest(
        Pagination pagination,
        String search,
        String email,
        Boolean isFeatured,
        List<String> ids) {

    public void validate(){
        if (pagination == null){
            throw new IllegalArgumentException("Pagination cannot be null");
        }
    }
}
