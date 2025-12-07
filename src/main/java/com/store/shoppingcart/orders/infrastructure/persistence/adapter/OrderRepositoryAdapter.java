package com.store.shoppingcart.orders.infrastructure.persistence.adapter;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.model.OrderStatus;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import com.store.shoppingcart.orders.infrastructure.persistence.entity.OrderJpaEntity;
import com.store.shoppingcart.orders.infrastructure.persistence.mapper.OrderMapper;
import com.store.shoppingcart.orders.infrastructure.persistence.repository.OrderJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderRepositoryAdapter implements OrderRepository {
    
    private final OrderJpaRepository jpaRepository;
    private final OrderMapper mapper;
    
    public OrderRepositoryAdapter(OrderJpaRepository jpaRepository, OrderMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = mapper.toEntity(order);
        OrderJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Order> findById(OrderId id) {
        return jpaRepository.findById(id.value().toString())
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Order> findByClientId(ClientId clientId) {
        return jpaRepository.findByClientId(clientId.value().toString()).stream()
            .map(mapper::toDomain)
            .toList();
    }
    
    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return jpaRepository.findByStatus(status).stream()
            .map(mapper::toDomain)
            .toList();
    }
}
