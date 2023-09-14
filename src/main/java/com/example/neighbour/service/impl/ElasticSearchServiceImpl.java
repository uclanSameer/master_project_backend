package com.example.neighbour.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.UpdateByQueryRequest;
import co.elastic.clients.json.JsonData;
import com.example.neighbour.service.ElasticSearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@AllArgsConstructor
@Slf4j
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
            log.error("Error while adding document to elastic search", e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Error while adding document to elastic search", e);
        }
    }

    @Override
    public void addCuisineToSeller(String id, String cuisine) {
        UpdateByQueryRequest updateByQueryRequest = UpdateByQueryRequest.of(
                u -> u
                        .index("seller")
                        .query(q -> q.match(m -> m.field("id").query(id)))
                        .script(s -> s.inline(builder -> builder
                                .source("if(ctx._source.containsKey('cuisines')) { ctx._source.cuisines.addAll(params.cuisines) } else { ctx._source.cuisines = params.cuisines }")
                                .params(
                                        "cuisines", JsonData.of(Collections.singletonList(cuisine))
                                )
                                .lang("painless")
                                )
                        )
        );
        try {
            esClient.updateByQuery(updateByQueryRequest);
        } catch (IOException e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Error while updating document to elastic search");
        }
    }
}
