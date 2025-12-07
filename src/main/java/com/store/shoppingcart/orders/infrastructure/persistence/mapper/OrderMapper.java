package com.store.shoppingcart.orders.infrastructure.persistence.mapper;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.orders.domain.model.*;
import com.store.shoppingcart.orders.infrastructure.persistence.entity.OrderItemJpaEntity;
import com.store.shoppingcart.orders.infrastructure.persistence.entity.OrderJpaEntity;
import com.store.shoppingcart.products.domain.model.ProductId;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Component
public class OrderMapper {
    
    public OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId().value().toString());
        entity.setClientId(order.getClientId().value().toString());
        entity.setTotalAmount(order.getTotalAmount().amount());
        entity.setCurrency(order.getTotalAmount().currency().getCurrencyCode());
        entity.setStatus(order.getStatus());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setUpdatedAt(order.getUpdatedAt());
        
        List<OrderItemJpaEntity> itemEntities = order.getItems().stream()
            .map(item -> toItemEntity(item, entity))
            .toList();
        entity.setItems(itemEntities);
        
        return entity;
    }
    
    public Order toDomain(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
            .map(this::toItemDomain)
            .toList();
        
        return new Order(
            new OrderId(UUID.fromString(entity.getId())),
            new ClientId(UUID.fromString(entity.getClientId())),
            items,
            new Money(entity.getTotalAmount(), Currency.getInstance(entity.getCurrency())),
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
    
    private OrderItemJpaEntity toItemEntity(OrderItem item, OrderJpaEntity orderEntity) {
        OrderItemJpaEntity entity = new OrderItemJpaEntity();
        entity.setId(item.getId());
        entity.setOrder(orderEntity);
        entity.setProductId(item.getProductId().value());
        entity.setProductName(item.getProductName());
        entity.setUnitPrice(item.getUnitPrice().amount());
        entity.setQuantity(item.getQuantity());
        entity.setSubtotal(item.getSubtotal().amount());
        return entity;
    }
    
    private OrderItem toItemDomain(OrderItemJpaEntity entity) {
        return new OrderItem(
            entity.getId(),
            new ProductId(entity.getProductId()),
            entity.getProductName(),
            new Money(entity.getUnitPrice(), Currency.getInstance("USD")),
            entity.getQuantity(),
            new Money(entity.getSubtotal(), Currency.getInstance("USD"))
        );
    }
}
