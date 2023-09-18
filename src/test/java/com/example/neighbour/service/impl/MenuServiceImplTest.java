package com.example.neighbour.service.impl;

import com.example.neighbour.data.BusinessTest;
import com.example.neighbour.data.MenuItem;
import com.example.neighbour.dto.MenuItemDto;
import com.example.neighbour.dto.MenuItemDtoTest;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.repositories.MenuRepository;
import com.example.neighbour.service.BusinessService;
import com.example.neighbour.service.ElasticAddUpdateService;
import com.example.neighbour.service.aws.S3Service;
import com.example.neighbour.utils.UserUtilsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    @Mock private MenuRepository menuItemRepository;

    @Mock private BusinessService businessService;

    @Mock private S3Service s3Service;

    @Mock private ElasticAddUpdateService<MenuItemDto> esService;

    @InjectMocks
    private MenuServiceImpl menuService;

    @Test
    void addMenuItem() {
        // Business users are only allowed to add menu items to their own business
        UserUtilsTest.SET_BUSINESS_USER_AUTHENTICATION();

        when(businessService.getCurrentBusiness()).thenReturn(BusinessTest.createBusiness());

        doNothing().when(s3Service).uploadFile(anyString(), anyString());

        when(menuItemRepository.save(any())).thenAnswer(i -> {
            MenuItem menuItemDto = i.getArgument(0);
            menuItemDto.setId(1);
            return menuItemDto;
        });

        doNothing().when(esService).addDocument(any(), anyString(), anyString());

        doNothing().when(esService).addCuisineToSeller(anyString(), anyString());

        MenuItemDto menuItemDto = MenuItemDtoTest.getMenuItemDto();

        ResponseDto<MenuItemDto> menuItemDtoResponseDto = menuService.addMenuItem(menuItemDto);


        assertEquals(menuItemDtoResponseDto.getMessage(), "Menu item added successfully.");

    }

}