package com.store.shoppingcart.products.entrypoint.rest.controller;

import com.store.shoppingcart.common.dto.ApiResponse;
import com.store.shoppingcart.products.domain.model.Category;
import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.model.ProductId;
import com.store.shoppingcart.products.domain.port.in.FilterProductsByCategoryUseCase;
import com.store.shoppingcart.products.domain.port.in.FindProductUseCase;
import com.store.shoppingcart.products.domain.port.in.ListProductsUseCase;
import com.store.shoppingcart.products.entrypoint.rest.dto.ProductResponse;
import com.store.shoppingcart.products.entrypoint.rest.mapper.ProductDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    
    private final FindProductUseCase findProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final FilterProductsByCategoryUseCase filterProductsUseCase;
    private final ProductDtoMapper mapper;
    
    public ProductController(FindProductUseCase findProductUseCase,
                            ListProductsUseCase listProductsUseCase,
                            FilterProductsByCategoryUseCase filterProductsUseCase,
                            ProductDtoMapper mapper) {
        this.findProductUseCase = findProductUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.filterProductsUseCase = filterProductsUseCase;
        this.mapper = mapper;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable String id) {
        ProductId productId = ProductId.from(id);
        Product product = findProductUseCase.execute(productId);
        
        ProductResponse response = mapper.toResponse(product);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), response));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> listProducts(
        @RequestParam(required = false) Integer limit
    ) {
        List<Product> products = limit != null
            ? listProductsUseCase.execute(limit)
            : listProductsUseCase.execute();
        
        List<ProductResponse> responses = products.stream()
            .map(mapper::toResponse)
            .toList();
        
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), responses));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> filterByCategory(@PathVariable String category) {
        Category cat = Category.fromValue(category);
        List<Product> products = filterProductsUseCase.execute(cat);
        
        List<ProductResponse> responses = products.stream()
            .map(mapper::toResponse)
            .toList();
        
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), responses));
    }
}
