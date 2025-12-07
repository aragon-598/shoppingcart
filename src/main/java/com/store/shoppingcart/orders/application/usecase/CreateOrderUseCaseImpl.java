package com.store.shoppingcart.orders.application.usecase;

import com.store.shoppingcart.orders.application.dto.CreateOrderCommand;
import com.store.shoppingcart.orders.domain.factory.OrderFactory;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.port.in.CreateOrderUseCase;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;
    
    public CreateOrderUseCaseImpl(OrderRepository orderRepository, OrderFactory orderFactory) {
        this.orderRepository = orderRepository;
        this.orderFactory = orderFactory;
    }
    
    @Override
    @Transactional
    public OrderId execute(CreateOrderCommand command) {
        Order order = orderFactory.create(
            command.clientId(),
            command.items()
        );
        
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }
}
