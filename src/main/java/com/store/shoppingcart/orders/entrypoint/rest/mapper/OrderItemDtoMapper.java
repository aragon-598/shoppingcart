package com.store.shoppingcart.orders.entrypoint.rest.mapper;

import com.store.shoppingcart.orders.application.dto.AddItemCommand;
import com.store.shoppingcart.orders.application.dto.UpdateQuantityCommand;
import com.store.shoppingcart.orders.domain.model.OrderItem;
import com.store.shoppingcart.orders.entrypoint.rest.dto.AddItemRequest;
import com.store.shoppingcart.orders.entrypoint.rest.dto.OrderItemResponse;
import com.store.shoppingcart.orders.entrypoint.rest.dto.UpdateQuantityRequest;
import com.store.shoppingcart.products.domain.model.ProductId;
import org.springframework.stereotype.Component;

@Component
public class OrderItemDtoMapper {
    
    public AddItemCommand toCommand(AddItemRequest request) {
        return new AddItemCommand(
            new ProductId(request.productId()),
            request.quantity()
        );
    }
    
    public UpdateQuantityCommand toCommand(UpdateQuantityRequest request) {
        return new UpdateQuantityCommand(request.quantity());
    }
    
    public OrderItemResponse toResponse(OrderItem item) {
        return new OrderItemResponse(
            item.getId(),
            item.getProductId().value(),
            item.getProductName(),
            item.getUnitPrice().formatted(),
            item.getQuantity(),
            item.getSubtotal().formatted()
        );
    }
}
