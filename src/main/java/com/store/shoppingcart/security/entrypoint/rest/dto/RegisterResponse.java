package com.store.shoppingcart.security.entrypoint.rest.dto;

public record RegisterResponse(
    String userId,
    UserInfoDto userInfo
) {}
