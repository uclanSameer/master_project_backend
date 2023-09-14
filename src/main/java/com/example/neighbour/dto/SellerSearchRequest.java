package com.example.neighbour.dto;

import java.util.List;

public record SellerSearchRequest(Location location, Pagination pagination, String search, String postalCode,
                                  Integer radius,
                                  List<String> cuisines) {
}
