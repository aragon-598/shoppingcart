package com.store.shoppingcart.orders.application.usecase;

import com.store.shoppingcart.orders.domain.exception.InvalidOrderStateException;
import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.port.in.UpdateOrderStatusUseCase;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import com.store.shoppingcart.payments.application.port.out.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {
    
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    
    public UpdateOrderStatusUseCaseImpl(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
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
        
        // Validar que existe al menos un pago exitoso
        boolean hasSuccessfulPayment = paymentRepository.findByOrderId(orderId).stream()
            .anyMatch(payment -> payment.isSuccessful());
        
        if (!hasSuccessfulPayment) {
            throw new InvalidOrderStateException(
                "No se puede enviar la orden sin un pago exitoso"
            );
        }
        
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
