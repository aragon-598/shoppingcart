package com.store.shoppingcart.security.application.usecase;

import com.store.shoppingcart.security.application.dto.AuthenticationCommand;
import com.store.shoppingcart.security.domain.exception.InvalidCredentialsException;
import com.store.shoppingcart.security.domain.exception.UserInactiveException;
import com.store.shoppingcart.security.domain.model.AuthToken;
import com.store.shoppingcart.security.domain.model.Email;
import com.store.shoppingcart.security.domain.model.User;
import com.store.shoppingcart.security.domain.port.in.AuthenticateUserUseCase;
import com.store.shoppingcart.security.domain.port.out.PasswordEncoder;
import com.store.shoppingcart.security.domain.port.out.TokenGenerator;
import com.store.shoppingcart.security.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;
    
    public AuthenticateUserUseCaseImpl(UserRepository userRepository, 
                                       PasswordEncoder passwordEncoder,
                                       TokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }
    
    @Override
    public AuthToken execute(AuthenticationCommand command) {
        Email email = new Email(command.email());
        
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new InvalidCredentialsException());
        
        if (!passwordEncoder.matches(command.password(), user.getPassword().value())) {
            throw new InvalidCredentialsException();
        }
        
        if (!user.isActive()) {
            throw new UserInactiveException();
        }
        
        return tokenGenerator.generate(user);
    }
}
