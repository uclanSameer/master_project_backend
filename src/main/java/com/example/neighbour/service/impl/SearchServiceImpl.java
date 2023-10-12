package com.example.neighbour.service.impl;

import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.neighbour.controller.FoodSearchService;
import com.example.neighbour.dto.MenuItemDto;
import com.example.neighbour.dto.MenuSearchRequest;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.SellerSearchRequest;
import com.example.neighbour.dto.business.BusinessDto;
import com.example.neighbour.dto.business.EsBusinessDto;
import com.example.neighbour.dto.users.UserDetailDto;
import com.example.neighbour.service.ElasticSearchService;
import com.example.neighbour.service.aws.S3Service;
import com.example.neighbour.utils.GeneralStringConstants;
import com.example.neighbour.utils.SearchUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SearchServiceImpl implements FoodSearchService {

    private final ElasticSearchService searchService;

    private final S3Service s3Service;

    @Override
    public ResponseDto<List<MenuItemDto>> searchMenu(MenuSearchRequest search) {
        SearchRequest query = SearchUtils.createQueryForMenu(search);
        log.info("Query: {}", query);
        SearchResponse<MenuItemDto> response = searchService.searchDocument("menu", query, MenuItemDto.class);
        List<MenuItemDto> menu = mapMenuResponse(response);
        return ResponseDto.success(menu, "Success");
    }


    @Override
    public ResponseDto<List<BusinessDto>> searchBusiness(SellerSearchRequest search) {
        SearchRequest query = SearchUtils.createQueryForBusinessSearch(search);
        SearchResponse<EsBusinessDto> response = searchService.searchDocument("seller", query, EsBusinessDto.class);
        List<BusinessDto> menu = mapResponse(response)
                .stream()
                .map(businessDto -> {
                    UserDetailDto userDetail = businessDto.userDetail();
                    if (userDetail != null) {
                        userDetail.setImageUrl(s3Service.generatePreSignedUrl(userDetail.getImageUrl()));
                    }
                    return new BusinessDto(
                            businessDto.email(),
                            businessDto.isFeatured(),
                            businessDto.location(),
                            businessDto.rating(),
                            businessDto.userDetail()
                    );
                }).toList();
        return ResponseDto.success(menu, "Success");
    }

    @Override
    public ResponseDto<EsBusinessDto> findChefById(String id) {
        Query matchQuery = SearchUtils.matchQuery("id", id);
        SearchRequest query = SearchRequest.of(
                builder -> builder
                        .index("seller")
                        .query(matchQuery)
        );
        SearchResponse<EsBusinessDto> seller = searchService.searchDocument("seller", query, EsBusinessDto.class);
        EsBusinessDto businessDto = mapResponse(seller).stream().findFirst().orElse(null);
        return ResponseDto.success(businessDto, "Success");
    }

    @Override
    public ResponseDto<List<String>> distinctCuisines() {
        List<String> cuisines = List.of();
        SearchResponse<BusinessDto> seller = searchService.searchDocument(
                "seller",
                SearchUtils.distinctCuisineQuery(),
                BusinessDto.class
        );
        Buckets<StringTermsBucket> buckets = seller.aggregations().get(GeneralStringConstants.DISTINCT_CUISINES).sterms().buckets();

        if (buckets.isArray()) {
            cuisines = buckets.array().stream().map(stringTermsBucket -> stringTermsBucket.key().toString()).collect(Collectors.toList());
        }
        return ResponseDto.success(cuisines, "Success");
    }

    @NotNull
    private static <T> List<T> mapResponse(SearchResponse<T> response) {
        return response
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    private List<MenuItemDto> mapMenuResponse(SearchResponse<MenuItemDto> response) {
        return response
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .map((MenuItemDto menuItemDto) -> {
                    String signedUrl;
                    if (menuItemDto != null && menuItemDto.image() != null) {
                        signedUrl = s3Service.generatePreSignedUrl(menuItemDto.image());
                        return new MenuItemDto(menuItemDto, signedUrl);
                    }
                    return menuItemDto;
                })
                .collect(Collectors.toList());
    }
}
