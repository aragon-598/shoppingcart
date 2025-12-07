package com.store.shoppingcart.security.domain.port.out;

import com.store.shoppingcart.security.domain.model.Email;
import com.store.shoppingcart.security.domain.model.User;
import com.store.shoppingcart.security.domain.model.UserId;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(Email email);
    Optional<User> findById(UserId id);
    boolean existsByEmail(Email email);
}
