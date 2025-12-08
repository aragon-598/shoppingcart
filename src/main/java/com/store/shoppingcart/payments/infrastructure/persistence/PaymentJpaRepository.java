package com.store.shoppingcart.payments.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, String> {
    
    List<PaymentJpaEntity> findByOrderId(String orderId);
}
