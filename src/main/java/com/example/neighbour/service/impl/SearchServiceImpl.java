package com.example.neighbour.service.impl;

import com.example.neighbour.controller.SearchService;
import com.example.neighbour.dto.*;
import com.example.neighbour.dto.business.BusinessDto;
import com.example.neighbour.repositories.BusinessRepository;
import com.example.neighbour.repositories.MenuRepository;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    private final MenuRepository menuService;
    private final BusinessRepository businessService;

    public SearchServiceImpl(MenuRepository menuService, BusinessRepository businessService) {
        this.menuService = menuService;
        this.businessService = businessService;
    }


    @Override
    public ResponseDto<MenuItemDto> searchMenu(MenuSearchRequest search) {
        return null;
    }

    @Override
    public ResponseDto<MenuItemDto> allMenu(SearchRequest search) {
        return null;
    }

    @Override
    public ResponseDto<BusinessDto> searchBusiness(SellerSearchRequest search) {
        return null;
    }

    @Override
    public ResponseDto<BusinessDto> findChefById(String id) {
        return null;
    }

    @Override
    public ResponseDto<List<BusinessDto>> distinctCuisines() {
        return null;
    }
}
