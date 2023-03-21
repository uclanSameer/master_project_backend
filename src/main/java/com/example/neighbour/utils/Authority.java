package com.example.neighbour.utils;

public final class Authority {

    public static final String ADMIN = "hasAuthority('ADMIN')";
    public static final String BUSINESS = "hasAuthority('BUSINESS')";

    public static final String BUSINESS_OR_ADMIN = "hasAnyAuthority('ADMIN')"
            + "|| (hasAuthority('BUSINESS')";

    private Authority() {
        throw new IllegalStateException("Utility class");
    }
}
