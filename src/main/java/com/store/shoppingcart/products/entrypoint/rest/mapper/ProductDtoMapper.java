package com.store.shoppingcart.products.entrypoint.rest.mapper;

import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.entrypoint.rest.dto.ProductResponse;
import com.store.shoppingcart.products.entrypoint.rest.dto.RatingResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    
    private static final int MIN_RELIABLE_RATING_COUNT = 10;
    
    public ProductResponse toResponse(Product product) {
        RatingResponse rating = new RatingResponse(
            product.getRating().rate(),
            product.getRating().count(),
            product.getRating().isReliable(MIN_RELIABLE_RATING_COUNT)
        );
        
        return new ProductResponse(
            product.getId().value().toString(),
            product.getTitle(),
            product.getDescription(),
            product.getPrice().formatted(),
            product.getCategory().name(),
            product.getImageUrl(),
            rating
        );
    }
}
