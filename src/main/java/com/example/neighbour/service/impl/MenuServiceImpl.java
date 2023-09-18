package com.example.neighbour.service.impl;

import com.example.neighbour.configuration.security.permissions.ROLE_BUSINESS;
import com.example.neighbour.data.Business;
import com.example.neighbour.data.MenuItem;
import com.example.neighbour.data.User;
import com.example.neighbour.dto.MenuItemDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.repositories.MenuRepository;
import com.example.neighbour.service.BusinessService;
import com.example.neighbour.service.ElasticAddUpdateService;
import com.example.neighbour.service.MenuService;
import com.example.neighbour.service.aws.S3Service;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.example.neighbour.utils.UserUtils.getAuthenticatedUser;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuItemRepository;
    private final BusinessService businessService;
    private final S3Service s3Service;
    private final ElasticAddUpdateService<MenuItemDto> esService;

    /**
     * Generates a unique key for the image to be stored in S3
     */
    private static String generateS3Key(MenuItemDto menuItemDto, User authenticatedUser) {
        return "image/" + authenticatedUser.getId() + "/" + menuItemDto.cuisine() + "/" + menuItemDto.name();
    }

    @ROLE_BUSINESS
    @Override
    public ResponseDto<MenuItemDto> addMenuItem(MenuItemDto menuItemDto) {
        try {
            log.info("Adding menu item.");
            MenuItem menuItem = new MenuItem(menuItemDto);
            User authenticatedUser = getAuthenticatedUser();
            Business business = businessService.getCurrentBusiness();
            String keyName = generateS3Key(menuItemDto, authenticatedUser);
            s3Service.uploadFile(keyName, menuItemDto.image());
            buildMenuItem(menuItem, business, keyName);
            menuItemRepository.save(menuItem);
            MenuItemDto esMenuItem = new MenuItemDto(menuItem);
            esService.addDocument(esMenuItem, "menu", menuItem.getId().toString());
            esService.addCuisineToSeller(business.getId().toString(), menuItemDto.cuisine());
            return ResponseDto.success(esMenuItem, "Menu item added successfully.");
        } catch (Exception e) {
            log.error("Error while creating menu item", e);
            throw new ResponseStatusException(
                    INTERNAL_SERVER_ERROR, "Error while creating menu item", e);
        }
    }

    @Override
    public ResponseDto<List<MenuItemDto>> getMenusByCuisine(String cuisine) {
        log.info("Getting menus by cuisine: {}", cuisine);
        List<MenuItemDto> menuItems = menuItemRepository.findAllByCuisine(cuisine)
                 .map(MenuItemDto::new)
                .toList();
        return ResponseDto.success(menuItems, "Menus fetched successfully.");
    }

    @Override
    public ResponseDto<List<MenuItemDto>> getFeaturedMenuItems() {
        log.info("Getting featured menus.");
        List<MenuItemDto> menuItems = menuItemRepository.findAllByIsFeatured(true)
                .map(MenuItemDto::new)
                .toList();

        return ResponseDto.success(menuItems, "Featured menus fetched successfully.");
    }

    @Override
    public List<MenuItem> getMenusByBusinessId(Integer sellerId) {
        log.info("Getting menus by seller id: {}", sellerId);
        Optional<List<MenuItem>> menuItems = menuItemRepository.findAllByBusinessId(sellerId);

        return menuItems
                .orElseThrow(() -> new ResponseStatusException(
                        INTERNAL_SERVER_ERROR, "Menu item not found."));
    }

    public MenuItemDto getMenuItemById(int menuItemId) {
        log.info("Getting menu item by id: {}", menuItemId);
        return menuItemRepository.findById(menuItemId)
                .map(menuItem -> {
                    String imageUrl = s3Service.generatePreSignedUrl(menuItem.getImage());
                    return new MenuItemDto(menuItem, imageUrl);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        INTERNAL_SERVER_ERROR, "Menu item not found."));
    }

    private static void buildMenuItem(MenuItem menuItem, Business business, String keyName) {
        menuItem.setImage(keyName);
        menuItem.setBusiness(business);
    }
}
