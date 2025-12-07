package com.store.shoppingcart.security.domain.port.in;

import com.store.shoppingcart.security.domain.model.UserId;

public interface ValidateTokenUseCase {
    UserId execute(String token);
}
