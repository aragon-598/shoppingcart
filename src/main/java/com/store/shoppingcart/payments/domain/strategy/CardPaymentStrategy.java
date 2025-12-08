package com.store.shoppingcart.payments.domain.strategy;

import com.store.shoppingcart.payments.domain.exception.InvalidCardException;
import com.store.shoppingcart.payments.domain.model.PaymentMethod;
import com.store.shoppingcart.payments.domain.model.TransactionId;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CardPaymentStrategy implements PaymentStrategy {
    
    private static final Random random = new Random();
    private static final double FAILURE_RATE = 0.05; // 5% de tasa de fallo
    
    @Override
    public PaymentResult process(PaymentRequest request) {
        // Validar datos de tarjeta
        if (request.cardDetails() == null) {
            throw new InvalidCardException("Los datos de la tarjeta son requeridos");
        }
        
        // Simular procesamiento
        simulateProcessing();
        
        // Simular fallo aleatorio
        if (random.nextDouble() < FAILURE_RATE) {
            return PaymentResult.failure("Tarjeta rechazada por el banco emisor");
        }
        
        // Generar ID de transacción
        TransactionId transactionId = TransactionId.generate(PaymentMethod.CARD);
        
        return PaymentResult.success(transactionId);
    }
    
    @Override
    public boolean supports(PaymentMethod method) {
        return method == PaymentMethod.CARD;
    }
    
    private void simulateProcessing() {
        try {
            Thread.sleep(500 + random.nextInt(1000)); // 0.5-1.5 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
