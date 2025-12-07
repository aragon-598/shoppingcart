package com.store.shoppingcart.orders.application.usecase;

import com.store.shoppingcart.clients.domain.model.ClientId;
import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.port.in.FindOrderUseCase;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindOrderUseCaseImpl implements FindOrderUseCase {
    
    private final OrderRepository orderRepository;
    
    public FindOrderUseCaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Override
    public Order execute(OrderId orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
    
    @Override
    public List<Order> findByClient(ClientId clientId) {
        return orderRepository.findByClientId(clientId);
    }
}
