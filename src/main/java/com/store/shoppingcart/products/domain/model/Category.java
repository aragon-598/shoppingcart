package com.store.shoppingcart.products.domain.model;

public enum Category {
    ELECTRONICS("electronics"),
    JEWELERY("jewelery"),
    MENS_CLOTHING("men's clothing"),
    WOMENS_CLOTHING("women's clothing"),
    OTHER("other");
    
    private final String value;
    
    Category(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static Category fromValue(String value) {
        for (Category category : values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return category;
            }
        }
        return OTHER;
    }
}
