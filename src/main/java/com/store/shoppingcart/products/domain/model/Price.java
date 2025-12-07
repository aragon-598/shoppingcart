package com.store.shoppingcart.products.domain.model;

import com.store.shoppingcart.products.domain.exception.InvalidProductDataException;

import java.math.BigDecimal;
import java.util.Currency;

public record Price(BigDecimal amount, Currency currency) {
    
    public Price {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductDataException("Price cannot be negative");
        }
        if (currency == null) {
            throw new InvalidProductDataException("Currency is required");
        }
    }
    
    public static Price of(double amount, String currencyCode) {
        return new Price(
            BigDecimal.valueOf(amount),
            Currency.getInstance(currencyCode)
        );
    }
    
    public boolean isGreaterThan(Price other) {
        validateSameCurrency(other);
        return amount.compareTo(other.amount) > 0;
    }
    
    public String formatted() {
        return String.format("%s%.2f", currency.getSymbol(), amount);
    }
    
    private void validateSameCurrency(Price other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("No se pueden comparar precios con diferentes monedas");
        }
    }
}
