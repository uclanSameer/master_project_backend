package com.example.neighbour.service;

public interface ElasticSearchService<T> {

    /**
     * Adds document to the elastic search index
     *
     * @param document - document to be added
     * @param index    - index to be added to
     * @param id       - id of the document
     */
    void addDocument(T document, String index, String id);
}
