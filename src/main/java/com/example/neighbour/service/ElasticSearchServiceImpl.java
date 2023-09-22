package com.example.neighbour.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final ElasticsearchClient esClient;

    @Override
    public <T> SearchResponse<T> searchDocument(String index, SearchRequest request, Class<T> clazz) {
        try {
            return esClient.search(request, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
