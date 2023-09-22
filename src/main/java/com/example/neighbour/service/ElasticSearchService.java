package com.example.neighbour.service;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;

public interface ElasticSearchService {

    <T> SearchResponse<T> searchDocument(String index, SearchRequest request, Class<T> clazz);
}
