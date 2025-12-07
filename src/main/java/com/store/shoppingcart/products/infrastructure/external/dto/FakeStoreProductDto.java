package com.store.shoppingcart.products.infrastructure.external.dto;

public record FakeStoreProductDto(
    Long id,
    String title,
    Double price,
    String description,
    String category,
    String image,
    FakeStoreRatingDto rating
) {}
