package com.store.shoppingcart.security.entrypoint.rest.dto;

import java.time.LocalDateTime;

public record AuthResponse(
    String token,
    LocalDateTime expiresAt,
    UserInfoDto userInfo
) {}
