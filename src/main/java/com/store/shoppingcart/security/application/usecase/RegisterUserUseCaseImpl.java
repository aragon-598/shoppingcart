package com.store.shoppingcart.security.application.usecase;

import com.store.shoppingcart.security.application.dto.RegisterUserCommand;
import com.store.shoppingcart.security.domain.exception.UserAlreadyExistsException;
import com.store.shoppingcart.security.domain.model.Email;
import com.store.shoppingcart.security.domain.model.Password;
import com.store.shoppingcart.security.domain.model.Role;
import com.store.shoppingcart.security.domain.model.User;
import com.store.shoppingcart.security.domain.model.UserId;
import com.store.shoppingcart.security.domain.port.in.RegisterUserUseCase;
import com.store.shoppingcart.security.domain.port.out.PasswordEncoder;
import com.store.shoppingcart.security.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public RegisterUserUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    @Transactional
    public User execute(RegisterUserCommand command) {
        Email email = new Email(command.email());
        
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
        
        String encodedPassword = passwordEncoder.encode(command.password());
        
        User user = new User(
            UserId.generate(),
            email,
            new Password(encodedPassword),
            command.firstName(),
            command.lastName(),
            Role.CUSTOMER,
            true,
            LocalDateTime.now()
        );
        
        return userRepository.save(user);
    }
}
