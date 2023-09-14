package com.example.neighbour.dto;

public record MenuSearchRequest(
        Pagination pagination,
        String search,
        String email,
        Boolean isFeatured) {

    public void validate(){
        if (pagination == null){
            throw new IllegalArgumentException("Pagination cannot be null");
        }
    }
}
