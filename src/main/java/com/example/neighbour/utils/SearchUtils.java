package com.example.neighbour.utils;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.example.neighbour.dto.MenuSearchRequest;
import com.example.neighbour.dto.SellerSearchRequest;

import java.util.List;

import static com.example.neighbour.utils.GeneralStringConstants.DISTINCT_CUISINES;


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
                                                b.must(matchQuery("businessEmail.keyword", request.email()));
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

    public static SearchRequest createQueryForBusinessSearch(SellerSearchRequest request) {
        return SearchRequest.of(
                builder -> {
                    builder.query(
                                    q -> q.bool(
                                            b -> {
                                                if (request.search() != null) {
                                                    b.should(
                                                            matchQuery("businessName", request.search())

                                                    );
                                                }
                                                if (request.cuisines() != null) {
                                                    Query cuisineQuery = termQuery("cuisines.keyword", request.cuisines());
                                                    Query isFeaturedQuery = matchQuery("isFeatured", "true");
                                                    b.must(cuisineQuery, isFeaturedQuery);
                                                }

                                                if (request.location() != null) {
                                                    b.filter(
                                                            builder1 -> builder1.geoDistance(
                                                                    builder2 -> builder2.field("location")
                                                                            .distance(request.radius().toString() + "km")
                                                                            .location(
                                                                                    builder3 -> builder3
                                                                                            .latlon(builder4 ->
                                                                                                    builder4.lat(request.location().latitude())
                                                                                                            .lon(request.location().longitude()
                                                                                                            )
                                                                                            )
                                                                            )
                                                            ));
                                                }
                                                return b;
                                            }
                                    )
                            )
                            .size(request.pagination().size())
                            .from(request.pagination().page() * request.pagination().size());
                    return builder;
                }
        );
    }

    public static Query matchQuery(String field, String value) {
        return Query.of(
                builder1 -> builder1
                        .match(
                                m -> m.field(field).query(value)
                        )
        );
    }

    public static SearchRequest distinctCuisineQuery() {
        return SearchRequest.of(
                builder -> builder
                        .aggregations(
                                DISTINCT_CUISINES,
                                builder1 -> builder1
                                        .terms(
                                                builder2 -> builder2.field("cuisines.keyword").size(1000)
                                        )
                        )
        );
    }

    private static Query termQuery(String key, List<String> terms) {
        return Query.of(builder1 -> builder1
                .terms(
                        builder2 -> builder2.field(key)
                                .terms(
                                        builder3 -> builder3.value(
                                                terms
                                                        .stream().map(
                                                                FieldValue::of
                                                        ).toList()
                                        )
                                )
                )
        );
    }
}
