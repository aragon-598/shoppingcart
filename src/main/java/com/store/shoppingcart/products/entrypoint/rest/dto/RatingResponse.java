package com.store.shoppingcart.products.entrypoint.rest.dto;

public record RatingResponse(
    double rate,
    int count,
    boolean isReliable
) {}
