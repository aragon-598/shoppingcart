package com.store.shoppingcart.products.domain.port.out;

import com.store.shoppingcart.products.domain.model.Category;
import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.model.ProductId;

import java.util.List;

public interface ProductProviderPort {
    Product findById(ProductId id);
    List<Product> findAll();
    List<Product> findAll(int limit);
    List<Product> findByCategory(Category category);
    List<Category> findAllCategories();
}
