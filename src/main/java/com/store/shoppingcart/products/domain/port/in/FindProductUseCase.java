package com.store.shoppingcart.products.domain.port.in;

import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.model.ProductId;

public interface FindProductUseCase {
    Product execute(ProductId productId);
}
