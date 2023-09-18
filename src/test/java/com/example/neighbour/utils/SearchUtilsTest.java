package com.example.neighbour.utils;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.example.neighbour.dto.Location;
import com.example.neighbour.dto.MenuSearchRequest;
import com.example.neighbour.dto.Pagination;
import com.example.neighbour.dto.SellerSearchRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.List;

import static com.example.neighbour.utils.GeneralStringConstants.DISTINCT_CUISINES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchUtilsTest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SearchUtilsTest.class);

    @Test
    public void testCreateQueryForMenu() {
        Pagination pagination = new Pagination(0, 10);
        String search = "burger";
        String email = "john@example.com";
        Boolean isFeatured = true;

        MenuSearchRequest request = new MenuSearchRequest(pagination, search, email, isFeatured);
        SearchRequest createQueryForMenu = SearchUtils.createQueryForMenu(request);
        log.info("createQueryForMenu: {}", createQueryForMenu.query());

        if (createQueryForMenu.query() != null) {
            assertEquals(2, createQueryForMenu.query().bool().must().size());
            assertEquals(3, createQueryForMenu.query().bool().should().size());
        }
    }

    @Test
    public void testCreateQueryForMenuWithNullEmail() {
        Pagination pagination = new Pagination(0, 10);

        MenuSearchRequest request = new MenuSearchRequest(pagination, null, null, null);
        SearchRequest createQueryForMenu = SearchUtils.createQueryForMenu(request);
        log.info("createQueryForMenu: {}", createQueryForMenu.query());
        if (createQueryForMenu.query() != null) {
            assertEquals(0, createQueryForMenu.query().bool().must().size());
        }
    }

    @Test
    public void testCreateQueryForBusiness() {
        Location location = new Location(43.6532, -79.3832);
        Pagination pagination = new Pagination(0, 10);
        String search = "burger";
        String postalCode = "M5V 1J9";
        Integer radius = 10;
        List<String> cuisines = List.of("Chinese", "Japanese");
        SellerSearchRequest request = new SellerSearchRequest(location, pagination, search, postalCode, radius, cuisines);
        SearchRequest createQueryForBusinessSearch = SearchUtils.createQueryForBusinessSearch(request);
        if (createQueryForBusinessSearch.query() != null) {
            log.info("createQueryForBusinessSearch: {}", createQueryForBusinessSearch.query());
        }

        int filterCount = createQueryForBusinessSearch.query().bool().filter().size();

        assertEquals(1, filterCount);
        assertEquals(2, createQueryForBusinessSearch.query().bool().must().size());
        assertEquals(1, createQueryForBusinessSearch.query().bool().should().size());
    }

    @Test
    public void testAggregationForDistinctCuisines() {
        SearchRequest searchRequest = SearchUtils.distinctCuisineQuery();
        log.info("searchRequest: {}", searchRequest);
        assertEquals(1, searchRequest.aggregations().size());
        assertTrue(searchRequest.aggregations().containsKey(DISTINCT_CUISINES));
    }
}
