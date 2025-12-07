package com.store.shoppingcart.security.entrypoint.rest.mapper;

import com.store.shoppingcart.security.application.dto.AuthenticationCommand;
import com.store.shoppingcart.security.application.dto.RegisterUserCommand;
import com.store.shoppingcart.security.entrypoint.rest.dto.LoginRequest;
import com.store.shoppingcart.security.entrypoint.rest.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthDtoMapper {
    
    public RegisterUserCommand toCommand(RegisterRequest request) {
        return new RegisterUserCommand(
            request.email(),
            request.password(),
            request.firstName(),
            request.lastName()
        );
    }
    
    public AuthenticationCommand toCommand(LoginRequest request) {
        return new AuthenticationCommand(
            request.email(),
            request.password()
        );
    }
}
