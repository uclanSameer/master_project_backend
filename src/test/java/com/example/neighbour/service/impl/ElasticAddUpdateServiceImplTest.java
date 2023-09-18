package com.example.neighbour.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.UpdateByQueryRequest;
import com.example.neighbour.dto.business.EsBusinessDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ElasticAddUpdateServiceImplTest {

    @Mock private ElasticsearchClient esClient;


    @Test
    void addDocument() throws IOException {
        Map<String,String> testDocument = Map.of("key1", "value1", "key2", "value2");

        when(esClient.index(any(IndexRequest.class))).thenReturn(null);

        ElasticAddUpdateServiceImpl<Map<String,String>> elasticSearchService = new ElasticAddUpdateServiceImpl<>(esClient);

        elasticSearchService.addDocument(testDocument, "testIndex", "testId");

        verify(esClient, times(1)).index(any(IndexRequest.class));
    }

    @Test
    void addCuisineToSeller() throws IOException {
        ElasticAddUpdateServiceImpl<EsBusinessDto> elasticSearchService = new ElasticAddUpdateServiceImpl<>(esClient);

        when(esClient.updateByQuery(any(UpdateByQueryRequest.class))).thenReturn(null);
        assertDoesNotThrow(() -> elasticSearchService.addCuisineToSeller("testId", "testCuisine"));
    }
}