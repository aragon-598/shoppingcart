package com.store.shoppingcart.payments.domain.strategy;

import com.store.shoppingcart.payments.domain.exception.InvalidPaymentDataException;
import com.store.shoppingcart.payments.domain.model.PaymentMethod;
import com.store.shoppingcart.payments.domain.model.TransactionId;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaypalPaymentStrategy implements PaymentStrategy {
    
    private static final Random random = new Random();
    private static final double FAILURE_RATE = 0.03; // 3% de tasa de fallo
    
    @Override
    public PaymentResult process(PaymentRequest request) {
        // Validar email de PayPal
        if (request.paypalEmail() == null || request.paypalEmail().isBlank()) {
            throw new InvalidPaymentDataException("El email de PayPal es requerido");
        }
        
        if (!isValidEmail(request.paypalEmail())) {
            throw new InvalidPaymentDataException("El formato del email de PayPal es inválido");
        }
        
        // Simular procesamiento
        simulateProcessing();
        
        // Simular fallo aleatorio
        if (random.nextDouble() < FAILURE_RATE) {
            return PaymentResult.failure("Cuenta PayPal no verificada o con fondos insuficientes");
        }
        
        // Generar ID de transacción
        TransactionId transactionId = TransactionId.generate(PaymentMethod.PAYPAL);
        
        return PaymentResult.success(transactionId);
    }
    
    @Override
    public boolean supports(PaymentMethod method) {
        return method == PaymentMethod.PAYPAL;
    }
    
    private void simulateProcessing() {
        try {
            Thread.sleep(300 + random.nextInt(700)); // 0.3-1.0 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
