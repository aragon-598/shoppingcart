package com.store.shoppingcart.orders.application.usecase;

import com.store.shoppingcart.orders.domain.exception.OrderItemNotFoundException;
import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.model.OrderItem;
import com.store.shoppingcart.orders.domain.port.in.FindOrderItemsUseCase;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindOrderItemsUseCaseImpl implements FindOrderItemsUseCase {
    
    private final OrderRepository orderRepository;
    
    public FindOrderItemsUseCaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Override
    public List<OrderItem> execute(OrderId orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        return order.getItems();
    }
    
    @Override
    public OrderItem findById(OrderId orderId, Long itemId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        return order.findItem(itemId)
            .orElseThrow(() -> new OrderItemNotFoundException(itemId));
    }
}
