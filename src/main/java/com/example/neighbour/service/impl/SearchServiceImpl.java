package com.example.neighbour.service.impl;

import com.example.neighbour.controller.FoodSearchService;
import com.example.neighbour.dto.*;
import com.example.neighbour.dto.business.BusinessDto;
import com.example.neighbour.repositories.BusinessRepository;
import com.example.neighbour.repositories.MenuRepository;
import com.example.neighbour.utils.SearchUtils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchServiceImpl implements FoodSearchService {

    private final MenuRepository menuService;
    private final BusinessRepository businessService;

    public SearchServiceImpl(MenuRepository menuService, BusinessRepository businessService) {
        this.menuService = menuService;
        this.businessService = businessService;
    }


    @Override
    public ResponseDto<MenuItemDto> searchMenu(MenuSearchRequest search) {
        co.elastic.clients.elasticsearch.core.SearchRequest query = SearchUtils.createQueryForMenu(search);
        log.info("Query: {}", query);
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
