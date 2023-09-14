package com.example.neighbour.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.util.ObjectBuilder;
import com.example.neighbour.dto.MenuSearchRequest;

import java.util.List;

public class SearchUtils {

    public static SearchRequest createQueryForMenu(MenuSearchRequest request) {
        return SearchRequest.of(
                builder -> builder
                        .query(
                                q -> q.bool(
                                        b -> {
                                            b.should(
                                                    Query.of(
                                                            builder1 -> builder1
                                                                    .match(
                                                                            m -> m.field("name").query(request.search())
                                                                    )
                                                    ),
                                                    Query.of(
                                                            builder1 -> builder1
                                                                    .match(
                                                                            m -> m.field("description").query(request.search())
                                                                    )
                                                    ),
                                                    Query.of(
                                                            builder1 -> builder1
                                                                    .match(
                                                                            m -> m.field("cuisine").query(request.search())
                                                                    )
                                                    )
                                            );
                                            if (request.email() != null){
                                                b.must(
                                                        Query.of(
                                                                builder1 -> builder1
                                                                        .match(
                                                                                m -> m.field("businessEmail.keyword").query(request.email())
                                                                        )
                                                        )
                                                );
                                            }

                                            if (request.isFeatured() != null){
                                                b.must(
                                                        Query.of(
                                                                builder1 -> builder1
                                                                        .match(
                                                                                m -> m.field("isFeatured").query(request.isFeatured())
                                                                        )
                                                        )
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
}
