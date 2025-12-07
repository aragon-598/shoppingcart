package com.store.shoppingcart.security.domain.port.in;

import com.store.shoppingcart.security.application.dto.RegisterUserCommand;
import com.store.shoppingcart.security.domain.model.User;

public interface RegisterUserUseCase {
    User execute(RegisterUserCommand command);
}
