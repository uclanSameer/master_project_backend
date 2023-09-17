package com.example.neighbour.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.example.neighbour.dto.MenuSearchRequest;


public class SearchUtils {

    public static SearchRequest createQueryForMenu(MenuSearchRequest request) {
        return SearchRequest.of(
                builder -> builder
                        .query(
                                q -> q.bool(
                                        b -> {
                                                if (request.search() != null){
                                                        b.should(
                                                                matchQuery( "name", request.search()),
                                                                matchQuery( "description", request.search()),
                                                                matchQuery( "cuisine", request.search())
                                                        );
                                                }
                                            if (request.email() != null){
                                                b.must(
                                                        matchQuery("businessEmail.keyword", request.email())
                                                );
                                            }

                                            if (request.isFeatured() != null){
                                                b.must(
                                                       matchQuery("isFeatured", request.isFeatured().toString())
                                                );
                                            }
                                            return b;
                                        }
                                )
                        )
                        .size(request.pagination().size())
                        .from(request.pagination().page() * request.pagination().size())
        );
    }

private static Query matchQuery( String field, String value) {
        return Query.of(
                    builder1 -> builder1
                            .match(
                                    m -> m.field(field).query(value)
                            )
            );
}
}
