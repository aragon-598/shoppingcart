package com.store.shoppingcart.security.application.dto;

public record AuthenticationCommand(
    String email,
    String password
) {}
