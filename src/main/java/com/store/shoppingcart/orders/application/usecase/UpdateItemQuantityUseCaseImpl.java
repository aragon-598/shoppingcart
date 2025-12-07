package com.store.shoppingcart.orders.application.usecase;

import com.store.shoppingcart.orders.application.dto.UpdateQuantityCommand;
import com.store.shoppingcart.orders.domain.exception.InvalidQuantityException;
import com.store.shoppingcart.orders.domain.exception.OrderItemNotFoundException;
import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.orders.domain.exception.OrderNotModifiableException;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.model.OrderItem;
import com.store.shoppingcart.orders.domain.port.in.UpdateItemQuantityUseCase;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateItemQuantityUseCaseImpl implements UpdateItemQuantityUseCase {
    
    private final OrderRepository orderRepository;
    
    public UpdateItemQuantityUseCaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Override
    @Transactional
    public void execute(OrderId orderId, Long itemId, UpdateQuantityCommand command) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        if (!order.isModifiable()) {
            throw new OrderNotModifiableException(orderId);
        }
        
        OrderItem item = order.findItem(itemId)
            .orElseThrow(() -> new OrderItemNotFoundException(itemId));
        
        if (command.quantity() < 1 || command.quantity() > 999) {
            throw new InvalidQuantityException("La cantidad debe estar entre 1 y 999");
        }
        
        item.setQuantity(command.quantity());
        order.recalculateTotal();
        
        orderRepository.save(order);
    }
}
