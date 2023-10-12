package com.example.neighbour.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final ElasticsearchClient esClient;

    @Override
    public <T> SearchResponse<T> searchDocument(String index, SearchRequest request, Class<T> clazz) {
        try {
            return esClient.search(request, clazz);
        } catch (Exception e) {
            log.error("Error while searching document", e);
            throw new RuntimeException(e);
        }
    }
}
