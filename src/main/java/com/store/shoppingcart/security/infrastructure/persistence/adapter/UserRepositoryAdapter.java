package com.store.shoppingcart.security.infrastructure.persistence.adapter;

import com.store.shoppingcart.security.domain.model.Email;
import com.store.shoppingcart.security.domain.model.User;
import com.store.shoppingcart.security.domain.model.UserId;
import com.store.shoppingcart.security.domain.port.out.UserRepository;
import com.store.shoppingcart.security.infrastructure.persistence.entity.UserJpaEntity;
import com.store.shoppingcart.security.infrastructure.persistence.mapper.UserMapper;
import com.store.shoppingcart.security.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {
    
    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;
    
    public UserRepositoryAdapter(UserJpaRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public User save(User user) {
        UserJpaEntity entity = mapper.toEntity(user);
        UserJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.value())
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.value())
            .map(mapper::toDomain);
    }
    
    @Override
    public boolean existsByEmail(Email email) {
        return jpaRepository.existsByEmail(email.value());
    }
}
