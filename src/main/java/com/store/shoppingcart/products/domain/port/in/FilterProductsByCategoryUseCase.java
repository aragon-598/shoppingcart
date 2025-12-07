package com.store.shoppingcart.products.domain.port.in;

import com.store.shoppingcart.products.domain.model.Category;
import com.store.shoppingcart.products.domain.model.Product;

import java.util.List;

public interface FilterProductsByCategoryUseCase {
    List<Product> execute(Category category);
}
