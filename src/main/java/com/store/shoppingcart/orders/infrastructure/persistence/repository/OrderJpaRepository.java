package com.store.shoppingcart.orders.infrastructure.persistence.repository;

import com.store.shoppingcart.orders.domain.model.OrderStatus;
import com.store.shoppingcart.orders.infrastructure.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, String> {
    List<OrderJpaEntity> findByClientId(String clientId);
    List<OrderJpaEntity> findByStatus(OrderStatus status);
}
