package com.store.shoppingcart.security.infrastructure.security.adapter;

import com.store.shoppingcart.security.domain.port.out.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoderAdapter implements PasswordEncoder {
    
    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    
    @Override
    public String encode(String rawPassword) {
        return bcrypt.encode(rawPassword);
    }
    
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return bcrypt.matches(rawPassword, encodedPassword);
    }
}
