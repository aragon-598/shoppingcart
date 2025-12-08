package com.store.shoppingcart.orders.application.usecase;

import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.port.in.CancelOrderUseCase;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import com.store.shoppingcart.payments.application.port.out.PaymentRepository;
import com.store.shoppingcart.payments.domain.model.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelOrderUseCaseImpl implements CancelOrderUseCase {
    
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    
    public CancelOrderUseCaseImpl(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }
    
    @Override
    @Transactional
    public void execute(OrderId orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        // Reembolsar todos los pagos exitosos antes de cancelar
        paymentRepository.findByOrderId(orderId).stream()
            .filter(Payment::isSuccessful)
            .forEach(payment -> {
                payment.refund();
                paymentRepository.save(payment);
            });
        
        order.cancel();
        orderRepository.save(order);
    }
}
