package com.example.neighbour.service;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.order.OrderItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessOrderServiceImpl implements BusinessOrderService {

    private final OrderService orderService;

    private final MenuService menuService;

    private final BusinessService businessService;

    @Override
    public ResponseDto<List<OrderItemResponse>> getOrdersForBusiness() {
        var business = businessService.getCurrentBusiness();
        var menuItems = menuService.getMenusByBusinessId(business.getId());
        return orderService.getOrdersForMenuItems(menuItems);
    }
}
