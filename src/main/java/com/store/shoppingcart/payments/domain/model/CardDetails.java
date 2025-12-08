package com.store.shoppingcart.payments.domain.model;

import com.store.shoppingcart.payments.domain.exception.InvalidCardException;

import java.time.LocalDate;

public record CardDetails(
    String cardNumber,
    String cardHolderName,
    String expirationMonth,
    String expirationYear,
    String cvv
) {
    private static final String CARD_NUMBER_REGEX = "^[0-9]{13,19}$";
    private static final String CVV_REGEX = "^[0-9]{3,4}$";
    
    public CardDetails {
        validateCardNumber(cardNumber);
        validateCardHolderName(cardHolderName);
        validateExpiration(expirationMonth, expirationYear);
        validateCvv(cvv);
    }
    
    private void validateCardNumber(String number) {
        if (number == null || number.isBlank()) {
            throw new InvalidCardException("El número de tarjeta es requerido");
        }
        
        String cleaned = number.replaceAll("\\s+", "");
        if (!cleaned.matches(CARD_NUMBER_REGEX)) {
            throw new InvalidCardException("Formato de número de tarjeta inválido");
        }
        
        if (!luhnCheck(cleaned)) {
            throw new InvalidCardException("Número de tarjeta inválido (verificación Luhn falló)");
        }
    }
    
    private void validateCardHolderName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidCardException("El nombre del titular es requerido");
        }
    }
    
    private void validateExpiration(String month, String year) {
        try {
            int monthInt = Integer.parseInt(month);
            if (monthInt < 1 || monthInt > 12) {
                throw new InvalidCardException("Mes de expiración inválido");
            }
            
            int yearInt = Integer.parseInt(year);
            int currentYear = LocalDate.now().getYear() % 100;
            int currentMonth = LocalDate.now().getMonthValue();
            
            if (yearInt < currentYear || (yearInt == currentYear && monthInt < currentMonth)) {
                throw new InvalidCardException("La tarjeta está expirada");
            }
        } catch (NumberFormatException e) {
            throw new InvalidCardException("Formato de fecha de expiración inválido");
        }
    }
    
    private void validateCvv(String cvv) {
        if (cvv == null || !cvv.matches(CVV_REGEX)) {
            throw new InvalidCardException("Formato de CVV inválido");
        }
    }
    
    private boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
    
    public String maskedCardNumber() {
        String cleaned = cardNumber.replaceAll("\\s+", "");
        int length = cleaned.length();
        return "**** **** **** " + cleaned.substring(length - 4);
    }
}
