package com.example.neighbour.service;

public interface ElasticAddUpdateService<T> {

    /**
     * Adds document to the elastic search index
     *
     * @param document - document to be added
     * @param index    - index to be added to
     * @param id       - id of the document
     */
    void addDocument(T document, String index, String id);


    /**
     * Adds cuisine to the seller
     *
     * @param id      - id of the seller
     * @param cuisine - cuisine to be added
     */
    void addCuisineToSeller(String id, String cuisine);
}
