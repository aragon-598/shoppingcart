package com.store.shoppingcart.payments.application.service;

import com.store.shoppingcart.payments.application.port.in.RefundPaymentUseCase;
import com.store.shoppingcart.payments.application.port.out.PaymentRepository;
import com.store.shoppingcart.payments.domain.exception.PaymentNotFoundException;
import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.model.PaymentId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RefundPaymentUseCaseImpl implements RefundPaymentUseCase {
    
    private final PaymentRepository paymentRepository;
    
    public RefundPaymentUseCaseImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    
    @Override
    public Payment refundPayment(PaymentId paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        
        payment.refund();
        
        return paymentRepository.save(payment);
    }
}
