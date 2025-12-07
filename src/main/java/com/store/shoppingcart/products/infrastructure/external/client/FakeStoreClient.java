package com.store.shoppingcart.products.infrastructure.external.client;

import com.store.shoppingcart.products.infrastructure.external.dto.FakeStoreProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
    name = "fake-store-api",
    url = "${external.fakestore.url}"
)
public interface FakeStoreClient {
    
    @GetMapping("/products")
    List<FakeStoreProductDto> getAllProducts();
    
    @GetMapping("/products")
    List<FakeStoreProductDto> getProducts(@RequestParam("limit") int limit);
    
    @GetMapping("/products/{id}")
    FakeStoreProductDto getProductById(@PathVariable("id") Long id);
    
    @GetMapping("/products/categories")
    List<String> getCategories();
    
    @GetMapping("/products/category/{category}")
    List<FakeStoreProductDto> getProductsByCategory(@PathVariable("category") String category);
}
