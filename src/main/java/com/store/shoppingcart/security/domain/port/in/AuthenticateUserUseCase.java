package com.store.shoppingcart.security.domain.port.in;

import com.store.shoppingcart.security.application.dto.AuthenticationCommand;
import com.store.shoppingcart.security.domain.model.AuthToken;

public interface AuthenticateUserUseCase {
    AuthToken execute(AuthenticationCommand command);
}
