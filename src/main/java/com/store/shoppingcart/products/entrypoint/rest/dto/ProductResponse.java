package com.store.shoppingcart.products.entrypoint.rest.dto;

public record ProductResponse(
    String id,
    String title,
    String description,
    String price,
    String category,
    String imageUrl,
    RatingResponse rating
) {}
