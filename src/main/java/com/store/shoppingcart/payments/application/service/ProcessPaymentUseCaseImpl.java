package com.store.shoppingcart.payments.application.service;

import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.model.OrderStatus;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.payments.application.port.in.ProcessPaymentUseCase;
import com.store.shoppingcart.payments.application.port.out.PaymentRepository;
import com.store.shoppingcart.payments.domain.exception.OrderNotPayableException;
import com.store.shoppingcart.payments.domain.exception.PaymentFailedException;
import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.model.PaymentId;
import com.store.shoppingcart.payments.domain.model.PaymentStatus;
import com.store.shoppingcart.payments.domain.strategy.PaymentRequest;
import com.store.shoppingcart.payments.domain.strategy.PaymentResult;
import com.store.shoppingcart.payments.domain.strategy.PaymentStrategy;
import com.store.shoppingcart.payments.domain.strategy.PaymentStrategyFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessPaymentUseCaseImpl implements ProcessPaymentUseCase {
    
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentStrategyFactory strategyFactory;
    
    public ProcessPaymentUseCaseImpl(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            PaymentStrategyFactory strategyFactory) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.strategyFactory = strategyFactory;
    }
    
    @Override
    public Payment processPayment(OrderId orderId, PaymentRequest paymentRequest) {
        // Buscar la orden
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        // Validar que la orden está en estado CONFIRMED
        if (order.getStatus() != OrderStatus.CONFIRMED) {
            throw new OrderNotPayableException(
                orderId, 
                "la orden debe estar confirmada. Estado actual: " + order.getStatus()
            );
        }
        
        // Validar que la orden no tiene pagos exitosos previos
        boolean hasSuccessfulPayment = paymentRepository.findByOrderId(orderId).stream()
            .anyMatch(Payment::isSuccessful);
        
        if (hasSuccessfulPayment) {
            throw new OrderNotPayableException(orderId, "la orden ya ha sido pagada");
        }
        
        // Crear el pago en estado PENDING
        Payment payment = new Payment(
            PaymentId.generate(),
            orderId,
            order.getTotalAmount(),
            paymentRequest.method()
        );
        
        // Guardar en estado PENDING
        payment = paymentRepository.save(payment);
        
        try {
            // Marcar como PROCESSING
            payment.markAsProcessing();
            payment = paymentRepository.save(payment);
            
            // Obtener la estrategia de pago
            PaymentStrategy strategy = strategyFactory.getStrategy(paymentRequest.method());
            
            // Procesar el pago
            PaymentResult result = strategy.process(paymentRequest);
            
            if (result.success()) {
                // Pago exitoso
                payment.markAsSuccessful(result.transactionId());
                payment = paymentRepository.save(payment);
                
                // Marcar la orden como pagada
                order.markAsPaid();
                orderRepository.save(order);
                
            } else {
                // Pago fallido
                payment.markAsFailed(result.message());
                payment = paymentRepository.save(payment);
            }
            
        } catch (Exception e) {
            // Manejar cualquier error durante el procesamiento
            payment.markAsFailed("Error al procesar el pago: " + e.getMessage());
            paymentRepository.save(payment);
            
            throw new PaymentFailedException("Error al procesar el pago", e);
        }
        
        return payment;
    }
}
