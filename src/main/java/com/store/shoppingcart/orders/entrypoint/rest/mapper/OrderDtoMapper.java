package com.store.shoppingcart.orders.entrypoint.rest.mapper;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.orders.application.dto.CreateOrderCommand;
import com.store.shoppingcart.orders.application.dto.OrderItemCommand;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderItem;
import com.store.shoppingcart.orders.entrypoint.rest.dto.CreateOrderRequest;
import com.store.shoppingcart.orders.entrypoint.rest.dto.OrderItemRequest;
import com.store.shoppingcart.orders.entrypoint.rest.dto.OrderItemResponse;
import com.store.shoppingcart.orders.entrypoint.rest.dto.OrderResponse;
import com.store.shoppingcart.products.domain.model.ProductId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDtoMapper {
    
    public CreateOrderCommand toCommand(CreateOrderRequest request) {
        ClientId clientId = new ClientId(UUID.fromString(request.clientId()));
        List<OrderItemCommand> items = request.items().stream()
            .map(this::toItemCommand)
            .toList();
        
        return new CreateOrderCommand(clientId, items);
    }
    
    public OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
            .map(this::toItemResponse)
            .toList();
        
        return new OrderResponse(
            order.getId().value().toString(),
            order.getClientId().value().toString(),
            items,
            order.getTotalAmount().formatted(),
            order.getStatus().name(),
            order.getTotalItems(),
            order.getCreatedAt().toString(),
            order.getUpdatedAt().toString()
        );
    }
    
    private OrderItemCommand toItemCommand(OrderItemRequest request) {
        return new OrderItemCommand(
            new ProductId(request.productId()),
            request.quantity()
        );
    }
    
    private OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
            item.getProductId().value(),
            item.getProductName(),
            item.getUnitPrice().formatted(),
            item.getQuantity(),
            item.getSubtotal().formatted()
        );
    }
}
