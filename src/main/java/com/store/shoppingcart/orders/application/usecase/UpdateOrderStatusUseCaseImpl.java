package com.store.shoppingcart.orders.application.usecase;

import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.port.in.UpdateOrderStatusUseCase;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {
    
    private final OrderRepository orderRepository;
    
    public UpdateOrderStatusUseCaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Override
    @Transactional
    public void confirm(OrderId orderId) {
        Order order = findOrder(orderId);
        order.confirm();
        orderRepository.save(order);
    }
    
    @Override
    @Transactional
    public void ship(OrderId orderId) {
        Order order = findOrder(orderId);
        order.ship();
        orderRepository.save(order);
    }
    
    @Override
    @Transactional
    public void deliver(OrderId orderId) {
        Order order = findOrder(orderId);
        order.deliver();
        orderRepository.save(order);
    }
    
    private Order findOrder(OrderId orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
