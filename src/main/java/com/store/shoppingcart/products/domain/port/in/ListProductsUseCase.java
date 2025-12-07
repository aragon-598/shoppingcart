package com.store.shoppingcart.products.domain.port.in;

import com.store.shoppingcart.products.domain.model.Product;

import java.util.List;

public interface ListProductsUseCase {
    List<Product> execute();
    List<Product> execute(int limit);
}
