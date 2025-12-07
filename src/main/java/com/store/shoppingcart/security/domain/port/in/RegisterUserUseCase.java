package com.store.shoppingcart.security.domain.port.in;

import com.store.shoppingcart.security.application.dto.RegisterUserCommand;
import com.store.shoppingcart.security.domain.model.UserId;

public interface RegisterUserUseCase {
    UserId execute(RegisterUserCommand command);
}
