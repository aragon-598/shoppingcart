package com.store.shoppingcart.products.application.usecase;

import com.store.shoppingcart.products.domain.exception.ExternalServiceException;
import com.store.shoppingcart.products.domain.exception.ProductNotFoundException;
import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.model.ProductId;
import com.store.shoppingcart.products.domain.port.in.FindProductUseCase;
import com.store.shoppingcart.products.domain.port.out.ProductProviderPort;
import org.springframework.stereotype.Service;

@Service
public class FindProductUseCaseImpl implements FindProductUseCase {
    
    private final ProductProviderPort productProvider;
    
    public FindProductUseCaseImpl(ProductProviderPort productProvider) {
        this.productProvider = productProvider;
    }
    
    @Override
    public Product execute(ProductId productId) {
        try {
            return productProvider.findById(productId);
        } catch (ExternalServiceException e) {
            throw new ProductNotFoundException(productId, e);
        }
    }
}
