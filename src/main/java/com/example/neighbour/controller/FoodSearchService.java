package com.example.neighbour.controller;

import com.example.neighbour.dto.*;
import com.example.neighbour.dto.business.BusinessDto;

import java.util.List;


public interface FoodSearchService {
    ResponseDto<List<MenuItemDto>> searchMenu(MenuSearchRequest search);
    ResponseDto<List<BusinessDto>> searchBusiness(SellerSearchRequest search);
    ResponseDto<BusinessDto>  findChefById(String id);
    ResponseDto<List<String>>  distinctCuisines();
}
