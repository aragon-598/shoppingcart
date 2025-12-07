package com.store.shoppingcart.security.entrypoint.rest.dto;

public record UserInfoDto(
    String id,
    String email,
    String firstName,
    String lastName
) {}
