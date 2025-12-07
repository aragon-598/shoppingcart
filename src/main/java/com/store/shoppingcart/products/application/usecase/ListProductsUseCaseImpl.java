package com.store.shoppingcart.products.application.usecase;

import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.port.in.ListProductsUseCase;
import com.store.shoppingcart.products.domain.port.out.ProductProviderPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProductsUseCaseImpl implements ListProductsUseCase {
    
    private final ProductProviderPort productProvider;
    
    public ListProductsUseCaseImpl(ProductProviderPort productProvider) {
        this.productProvider = productProvider;
    }
    
    @Override
    public List<Product> execute() {
        return productProvider.findAll();
    }
    
    @Override
    public List<Product> execute(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be positive");
        }
        return productProvider.findAll(limit);
    }
}
