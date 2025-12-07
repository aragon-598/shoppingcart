package com.store.shoppingcart.security.domain.port.out;

import com.store.shoppingcart.security.domain.model.AuthToken;
import com.store.shoppingcart.security.domain.model.User;
import com.store.shoppingcart.security.domain.model.UserId;

public interface TokenGenerator {
    AuthToken generate(User user);
    UserId validate(String token);
}
