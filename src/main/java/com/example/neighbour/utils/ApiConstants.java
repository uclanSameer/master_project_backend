package com.example.neighbour.utils;

public class ApiConstants {

    public static final String API_VERSION_1 = "/api/v1/";
    public static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui",
    };

    private ApiConstants() {
        throw new IllegalStateException("Utility class");
    }
}
