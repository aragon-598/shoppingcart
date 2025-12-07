package com.store.shoppingcart.orders.application.usecase;

import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.orders.domain.exception.OrderNotModifiableException;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.port.in.RemoveItemFromOrderUseCase;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveItemFromOrderUseCaseImpl implements RemoveItemFromOrderUseCase {
    
    private final OrderRepository orderRepository;
    
    public RemoveItemFromOrderUseCaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Override
    @Transactional
    public void execute(OrderId orderId, Long itemId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        if (!order.isModifiable()) {
            throw new OrderNotModifiableException(orderId);
        }
        
        order.removeItem(itemId);
        orderRepository.save(order);
    }
}
