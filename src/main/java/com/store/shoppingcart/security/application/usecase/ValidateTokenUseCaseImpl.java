package com.store.shoppingcart.security.application.usecase;

import com.store.shoppingcart.security.domain.model.UserId;
import com.store.shoppingcart.security.domain.port.in.ValidateTokenUseCase;
import com.store.shoppingcart.security.domain.port.out.TokenGenerator;
import org.springframework.stereotype.Service;

@Service
public class ValidateTokenUseCaseImpl implements ValidateTokenUseCase {
    
    private final TokenGenerator tokenGenerator;
    
    public ValidateTokenUseCaseImpl(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }
    
    @Override
    public UserId execute(String token) {
        return tokenGenerator.validate(token);
    }
}
