package com.store.shoppingcart.payments.application.service;

import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.payments.application.port.in.FindPaymentUseCase;
import com.store.shoppingcart.payments.application.port.out.PaymentRepository;
import com.store.shoppingcart.payments.domain.exception.PaymentNotFoundException;
import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.model.PaymentId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FindPaymentUseCaseImpl implements FindPaymentUseCase {
    
    private final PaymentRepository paymentRepository;
    
    public FindPaymentUseCaseImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    
    @Override
    public Payment findById(PaymentId paymentId) {
        return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }
    
    @Override
    public List<Payment> findByOrderId(OrderId orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
