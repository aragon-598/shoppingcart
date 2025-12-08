package com.store.shoppingcart.payments.infrastructure.persistence;

import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.payments.application.port.out.PaymentRepository;
import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.model.PaymentId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepositoryAdapter implements PaymentRepository {
    
    private final PaymentJpaRepository jpaRepository;
    private final PaymentMapper mapper;
    
    public PaymentRepositoryAdapter(PaymentJpaRepository jpaRepository, PaymentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Payment save(Payment payment) {
        PaymentJpaEntity entity = mapper.toEntity(payment);
        PaymentJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Payment> findById(PaymentId paymentId) {
        return jpaRepository.findById(paymentId.value().toString())
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Payment> findByOrderId(OrderId orderId) {
        return jpaRepository.findByOrderId(orderId.value().toString()).stream()
            .map(mapper::toDomain)
            .toList();
    }
}
