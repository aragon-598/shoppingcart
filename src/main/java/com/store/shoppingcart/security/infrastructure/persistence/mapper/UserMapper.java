package com.store.shoppingcart.security.infrastructure.persistence.mapper;

import com.store.shoppingcart.security.domain.model.Email;
import com.store.shoppingcart.security.domain.model.Password;
import com.store.shoppingcart.security.domain.model.Role;
import com.store.shoppingcart.security.domain.model.User;
import com.store.shoppingcart.security.domain.model.UserId;
import com.store.shoppingcart.security.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    
    public UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(
            user.getId().value().toString(),
            user.getEmail().value(),
            user.getPassword().value(),
            user.getFirstName(),
            user.getLastName(),
            user.getRole(),
            user.isActive(),
            user.getCreatedAt()
        );
    }
    
    public User toDomain(UserJpaEntity entity) {
        return new User(
            new UserId(UUID.fromString(entity.getId())),
            new Email(entity.getEmail()),
            new Password(entity.getPassword()),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getRole(),
            entity.isActive(),
            entity.getCreatedAt()
        );
    }
}
