package com.store.shoppingcart.orders.domain.factory;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.orders.application.dto.OrderItemCommand;
import com.store.shoppingcart.orders.domain.exception.ClientNotActiveException;
import com.store.shoppingcart.orders.domain.exception.EmptyOrderException;
import com.store.shoppingcart.orders.domain.model.*;
import com.store.shoppingcart.orders.domain.port.out.ClientValidatorPort;
import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.port.out.ProductProviderPort;

import java.time.LocalDateTime;
import java.util.List;

public class OrderFactoryImpl implements OrderFactory {
    
    private final ClientValidatorPort clientValidator;
    private final ProductProviderPort productProvider;
    
    public OrderFactoryImpl(ClientValidatorPort clientValidator, ProductProviderPort productProvider) {
        this.clientValidator = clientValidator;
        this.productProvider = productProvider;
    }
    
    @Override
    public Order create(ClientId clientId, List<OrderItemCommand> items) {
        validateClient(clientId);
        validateItems(items);
        
        List<OrderItem> orderItems = buildOrderItems(items);
        Money totalAmount = calculateTotal(orderItems);
        
        return new Order(
            OrderId.generate(),
            clientId,
            orderItems,
            totalAmount,
            OrderStatus.PENDING,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }
    
    private void validateClient(ClientId clientId) {
        if (!clientValidator.isActive(clientId)) {
            throw new ClientNotActiveException(clientId);
        }
    }
    
    private void validateItems(List<OrderItemCommand> items) {
        if (items == null || items.isEmpty()) {
            throw new EmptyOrderException();
        }
    }
    
    private List<OrderItem> buildOrderItems(List<OrderItemCommand> commands) {
        return commands.stream()
            .map(this::buildOrderItem)
            .toList();
    }
    
    private OrderItem buildOrderItem(OrderItemCommand command) {
        Product product = productProvider.findById(command.productId());
        Money unitPrice = new Money(
            product.getPrice().amount(),
            product.getPrice().currency()
        );
        
        return new OrderItem(
            command.productId(),
            product.getTitle(),
            unitPrice,
            command.quantity()
        );
    }
    
    private Money calculateTotal(List<OrderItem> items) {
        return items.stream()
            .map(OrderItem::getSubtotal)
            .reduce(Money.zero("USD"), Money::add);
    }
}
