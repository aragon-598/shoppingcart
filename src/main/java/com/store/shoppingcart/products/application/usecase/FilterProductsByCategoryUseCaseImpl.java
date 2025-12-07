package com.store.shoppingcart.products.application.usecase;

import com.store.shoppingcart.products.domain.model.Category;
import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.port.in.FilterProductsByCategoryUseCase;
import com.store.shoppingcart.products.domain.port.out.ProductProviderPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterProductsByCategoryUseCaseImpl implements FilterProductsByCategoryUseCase {
    
    private final ProductProviderPort productProvider;
    
    public FilterProductsByCategoryUseCaseImpl(ProductProviderPort productProvider) {
        this.productProvider = productProvider;
    }
    
    @Override
    public List<Product> execute(Category category) {
        return productProvider.findByCategory(category);
    }
}
