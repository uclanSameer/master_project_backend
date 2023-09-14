package com.example.neighbour.controller;

import com.example.neighbour.dto.*;
import com.example.neighbour.dto.business.BusinessDto;

import java.util.List;

public interface SearchService {
    ResponseDto<MenuItemDto> searchMenu(MenuSearchRequest search);
    ResponseDto<MenuItemDto> allMenu(SearchRequest search);
    ResponseDto<BusinessDto> searchBusiness(SellerSearchRequest search);
    ResponseDto<BusinessDto>  findChefById(String id);
    ResponseDto<List<BusinessDto>>  distinctCuisines();
}
