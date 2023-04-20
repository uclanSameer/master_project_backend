package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.cart.CartInfo;
import com.example.neighbour.dto.cart.CartTotalDto;
import com.example.neighbour.dto.order.OrderItemDto;
import com.example.neighbour.service.CartService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApiConstants.API_VERSION_1 + "cart")
public class ApplicationVersionController {

    @GetMapping("/version")
    public ResponseDto<String> getVersion() {
        return ResponseDto.success("1.0.0", "Version");
    }
}
