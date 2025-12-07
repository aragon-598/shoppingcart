package com.store.shoppingcart.orders.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {
    
    public Money {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency is required");
        }
    }
    
    public static Money zero(String currencyCode) {
        return new Money(BigDecimal.ZERO, Currency.getInstance(currencyCode));
    }
    
    public static Money of(double amount, String currencyCode) {
        return new Money(
            BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP),
            Currency.getInstance(currencyCode)
        );
    }
    
    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }
    
    public Money multiply(int multiplier) {
        return new Money(
            amount.multiply(BigDecimal.valueOf(multiplier)),
            currency
        );
    }
    
    public boolean isGreaterThan(Money other) {
        validateSameCurrency(other);
        return amount.compareTo(other.amount) > 0;
    }
    
    public String formatted() {
        return String.format("%s %.2f", currency.getSymbol(), amount);
    }
    
    private void validateSameCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("No se pueden realizar operaciones con diferentes monedas");
        }
    }
}
