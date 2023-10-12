package com.example.neighbour.controller;

import com.example.neighbour.dto.*;
import com.example.neighbour.dto.business.BusinessDto;
import com.example.neighbour.dto.business.EsBusinessDto;

import java.util.List;


public interface FoodSearchService {
    ResponseDto<List<MenuItemDto>> searchMenu(MenuSearchRequest search);
    ResponseDto<List<BusinessDto>> searchBusiness(SellerSearchRequest search);
    ResponseDto<EsBusinessDto>  findChefById(String id);
    ResponseDto<List<String>>  distinctCuisines();
}
