package com.example.neighbour.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.neighbour.dto.MenuSearchRequest;
import com.example.neighbour.dto.Pagination;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchUtilsTest {

    @Test
    public void testCreateQueryForMenu() {
        Pagination pagination = new Pagination(0, 10);
        String search = "burger";
        String email = "john@example.com";
        Boolean isFeatured = true;

        MenuSearchRequest request = new MenuSearchRequest(pagination, search, email, isFeatured);
        SearchRequest createQueryForMenu = SearchUtils.createQueryForMenu(request);
        String string = createQueryForMenu.query().toString();
        log.info("createQueryForMenu: {}", string);
    }

    @Test
    public void testCreateQueryForMenuWithNullEmail() {
        Pagination pagination = new Pagination(0, 10);
        String search = null;
        String email = null;
        Boolean isFeatured = null;

        MenuSearchRequest request = new MenuSearchRequest(pagination, search, email, isFeatured);
        SearchRequest createQueryForMenu = SearchUtils.createQueryForMenu(request);
        final String string = createQueryForMenu.query().toString();
        log.info("createQueryForMenu: {}", string);
    }
}
