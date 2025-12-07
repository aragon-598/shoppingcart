package com.store.shoppingcart.products.infrastructure.external.mapper;

import com.store.shoppingcart.products.domain.model.Category;
import com.store.shoppingcart.products.domain.model.Price;
import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.model.ProductId;
import com.store.shoppingcart.products.domain.model.Rating;
import com.store.shoppingcart.products.infrastructure.external.dto.FakeStoreProductDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FakeStoreProductMapper {
    
    public Product toDomain(FakeStoreProductDto dto) {
        return new Product(
            new ProductId(dto.id()),
            dto.title(),
            dto.description(),
            Price.of(dto.price(), "USD"),
            Category.fromValue(dto.category()),
            dto.image(),
            new Rating(dto.rating().rate(), dto.rating().count())
        );
    }
    
    public List<Product> toDomain(List<FakeStoreProductDto> dtos) {
        return dtos.stream()
            .map(this::toDomain)
            .toList();
    }
}
