package com.store.shoppingcart.payments.domain.strategy;

import com.store.shoppingcart.payments.domain.exception.InvalidPaymentDataException;
import com.store.shoppingcart.payments.domain.model.PaymentMethod;
import com.store.shoppingcart.payments.domain.model.TransactionId;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;

@Component
public class CryptoPaymentStrategy implements PaymentStrategy {
    
    private static final Random random = new Random();
    private static final double FAILURE_RATE = 0.02; // 2% de tasa de fallo
    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("BTC", "ETH", "USDT", "USDC");
    
    @Override
    public PaymentResult process(PaymentRequest request) {
        // Validar dirección de wallet
        if (request.walletAddress() == null || request.walletAddress().isBlank()) {
            throw new InvalidPaymentDataException("La dirección de wallet es requerida");
        }
        
        // Validar criptomoneda
        if (request.cryptoCurrency() == null || request.cryptoCurrency().isBlank()) {
            throw new InvalidPaymentDataException("La criptomoneda es requerida");
        }
        
        if (!SUPPORTED_CURRENCIES.contains(request.cryptoCurrency().toUpperCase())) {
            throw new InvalidPaymentDataException(
                "Criptomoneda no soportada. Monedas aceptadas: " + String.join(", ", SUPPORTED_CURRENCIES)
            );
        }
        
        // Simular procesamiento
        simulateProcessing();
        
        // Simular fallo aleatorio
        if (random.nextDouble() < FAILURE_RATE) {
            return PaymentResult.failure("Saldo insuficiente en la wallet o red congestionada");
        }
        
        // Generar ID de transacción
        TransactionId transactionId = TransactionId.generate(PaymentMethod.CRYPTO);
        
        return PaymentResult.success(transactionId);
    }
    
    @Override
    public boolean supports(PaymentMethod method) {
        return method == PaymentMethod.CRYPTO;
    }
    
    private void simulateProcessing() {
        try {
            Thread.sleep(1000 + random.nextInt(2000)); // 1.0-3.0 segundos (más lento por confirmaciones)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
