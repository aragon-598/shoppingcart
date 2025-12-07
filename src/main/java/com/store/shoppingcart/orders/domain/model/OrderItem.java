package com.store.shoppingcart.orders.domain.model;

import com.store.shoppingcart.products.domain.model.ProductId;

import java.math.BigDecimal;

public class OrderItem {
    
    private Long id;
    private ProductId productId;
    private String productName;
    private Money unitPrice;
    private int quantity;
    private Money subtotal;
    
    public OrderItem(ProductId productId, String productName, Money unitPrice, int quantity) {
        validateQuantity(quantity);
        
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }
    
    public OrderItem(Long id, ProductId productId, String productName, Money unitPrice, int quantity, Money subtotal) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }
    
    public void increaseQuantity(int amount) {
        validateQuantity(amount);
        this.quantity += amount;
        this.subtotal = calculateSubtotal();
    }
    
    public void decreaseQuantity(int amount) {
        int newQuantity = this.quantity - amount;
        validateQuantity(newQuantity);
        this.quantity = newQuantity;
        this.subtotal = calculateSubtotal();
    }
    
    private Money calculateSubtotal() {
        return new Money(
            unitPrice.amount().multiply(BigDecimal.valueOf(quantity)),
            unitPrice.currency()
        );
    }
    
    private void validateQuantity(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public ProductId getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public Money getUnitPrice() {
        return unitPrice;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public Money getSubtotal() {
        return subtotal;
    }
}
