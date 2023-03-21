package com.example.neighbour.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.example.neighbour.service.ElasticSearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@AllArgsConstructor
public class ElasticSearchServiceImpl<T> implements ElasticSearchService<T> {

    private final ElasticsearchClient esClient;

    @Override
    public void addDocument(T document, String index, String id) {
        try {
            IndexRequest<T> request = IndexRequest.of(i -> i
                    .index(index)
                    .id(id)
                    .document(document)
            );
            esClient.index(request);
        } catch (IOException e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Error while adding document to elastic search");
        }
    }
}
