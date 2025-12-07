package com.store.shoppingcart.products.infrastructure.external.adapter;

import com.store.shoppingcart.products.domain.exception.ExternalServiceException;
import com.store.shoppingcart.products.domain.exception.ProductNotFoundException;
import com.store.shoppingcart.products.domain.model.Category;
import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.model.ProductId;
import com.store.shoppingcart.products.domain.port.out.ProductProviderPort;
import com.store.shoppingcart.products.infrastructure.external.client.FakeStoreClient;
import com.store.shoppingcart.products.infrastructure.external.dto.FakeStoreProductDto;
import com.store.shoppingcart.products.infrastructure.external.mapper.FakeStoreProductMapper;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FakeStoreProductAdapter implements ProductProviderPort {
    
    private final FakeStoreClient client;
    private final FakeStoreProductMapper mapper;
    
    public FakeStoreProductAdapter(FakeStoreClient client, FakeStoreProductMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }
    
    @Override
    @CircuitBreaker(name = "fakestore", fallbackMethod = "findByIdFallback")
    @Retry(name = "fakestore")
    public Product findById(ProductId id) {
        try {
            FakeStoreProductDto dto = client.getProductById(id.value());
            return mapper.toDomain(dto);
        } catch (FeignException.NotFound e) {
            throw new ProductNotFoundException(id);
        } catch (FeignException e) {
            throw new ExternalServiceException("FakeStore API unavailable", e);
        }
    }
    
    @Override
    @CircuitBreaker(name = "fakestore", fallbackMethod = "findAllNoLimitFallback")
    @Retry(name = "fakestore")
    public List<Product> findAll() {
        try {
            List<FakeStoreProductDto> dtos = client.getAllProducts();
            return mapper.toDomain(dtos);
        } catch (FeignException e) {
            throw new ExternalServiceException("FakeStore API unavailable", e);
        }
    }
    
    @Override
    @CircuitBreaker(name = "fakestore", fallbackMethod = "findAllWithLimitFallback")
    @Retry(name = "fakestore")
    public List<Product> findAll(int limit) {
        try {
            List<FakeStoreProductDto> dtos = client.getProducts(limit);
            return mapper.toDomain(dtos);
        } catch (FeignException e) {
            throw new ExternalServiceException("FakeStore API unavailable", e);
        }
    }
    
    @Override
    @CircuitBreaker(name = "fakestore", fallbackMethod = "findByCategoryFallback")
    @Retry(name = "fakestore")
    public List<Product> findByCategory(Category category) {
        try {
            List<FakeStoreProductDto> dtos = client.getProductsByCategory(category.getValue());
            return mapper.toDomain(dtos);
        } catch (FeignException e) {
            throw new ExternalServiceException("FakeStore API unavailable", e);
        }
    }
    
    @Override
    @CircuitBreaker(name = "fakestore", fallbackMethod = "findAllCategoriesFallback")
    @Retry(name = "fakestore")
    public List<Category> findAllCategories() {
        try {
            List<String> categories = client.getCategories();
            return categories.stream()
                .map(Category::fromValue)
                .toList();
        } catch (FeignException e) {
            throw new ExternalServiceException("FakeStore API unavailable", e);
        }
    }
    
    // Fallback methods
    private Product findByIdFallback(ProductId id, Throwable t) {
        throw new ExternalServiceException("Product service temporarily unavailable. Please try again later.", t);
    }
    
    private List<Product> findAllNoLimitFallback(Throwable t) {
        throw new ExternalServiceException("Product service temporarily unavailable. Please try again later.", t);
    }
    
    private List<Product> findAllWithLimitFallback(int limit, Throwable t) {
        throw new ExternalServiceException("Product service temporarily unavailable. Please try again later.", t);
    }
    
    private List<Product> findByCategoryFallback(Category category, Throwable t) {
        throw new ExternalServiceException("Product service temporarily unavailable. Please try again later.", t);
    }
    
    private List<Category> findAllCategoriesFallback(Throwable t) {
        throw new ExternalServiceException("Product service temporarily unavailable. Please try again later.", t);
    }
    
}
