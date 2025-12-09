package com.store.shoppingcart.orders.entrypoint.rest.controller;

import com.store.shoppingcart.common.dto.ApiResponse;
import com.store.shoppingcart.orders.application.dto.AddItemCommand;
import com.store.shoppingcart.orders.application.dto.UpdateQuantityCommand;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.model.OrderItem;
import com.store.shoppingcart.orders.domain.port.in.AddItemToOrderUseCase;
import com.store.shoppingcart.orders.domain.port.in.FindOrderItemsUseCase;
import com.store.shoppingcart.orders.domain.port.in.RemoveItemFromOrderUseCase;
import com.store.shoppingcart.orders.domain.port.in.UpdateItemQuantityUseCase;
import com.store.shoppingcart.orders.entrypoint.rest.dto.AddItemRequest;
import com.store.shoppingcart.orders.entrypoint.rest.dto.OrderItemResponse;
import com.store.shoppingcart.orders.entrypoint.rest.dto.UpdateQuantityRequest;
import com.store.shoppingcart.orders.entrypoint.rest.mapper.OrderItemDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders/{orderId}/items")
public class OrderItemController {
    
    private final AddItemToOrderUseCase addItemUseCase;
    private final RemoveItemFromOrderUseCase removeItemUseCase;
    private final UpdateItemQuantityUseCase updateQuantityUseCase;
    private final FindOrderItemsUseCase findItemsUseCase;
    private final OrderItemDtoMapper mapper;
    
    public OrderItemController(AddItemToOrderUseCase addItemUseCase, RemoveItemFromOrderUseCase removeItemUseCase,
                               UpdateItemQuantityUseCase updateQuantityUseCase, FindOrderItemsUseCase findItemsUseCase,
                               OrderItemDtoMapper mapper) {
        this.addItemUseCase = addItemUseCase;
        this.removeItemUseCase = removeItemUseCase;
        this.updateQuantityUseCase = updateQuantityUseCase;
        this.findItemsUseCase = findItemsUseCase;
        this.mapper = mapper;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addItem(
        @PathVariable String orderId,
        @Valid @RequestBody AddItemRequest request
    ) {
        OrderId id = OrderId.from(orderId);
        AddItemCommand command = mapper.toCommand(request);
        
        addItemUseCase.execute(id, command);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED.value(), "Item agregado exitosamente"));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderItemResponse>>> getItems(@PathVariable String orderId) {
        OrderId id = OrderId.from(orderId);
        List<OrderItem> items = findItemsUseCase.execute(id);
        
        List<OrderItemResponse> responses = items.stream()
            .map(mapper::toResponse)
            .toList();
        
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), responses));
    }
    
    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<OrderItemResponse>> getItem(
        @PathVariable String orderId,
        @PathVariable Long itemId
    ) {
        OrderId orderIdObj = OrderId.from(orderId);
        
        OrderItem item = findItemsUseCase.findById(orderIdObj, itemId);
        OrderItemResponse response = mapper.toResponse(item);
        
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), response));
    }
    
    @PatchMapping("/{itemId}/quantity")
    public ResponseEntity<ApiResponse<String>> updateQuantity(
        @PathVariable String orderId,
        @PathVariable Long itemId,
        @Valid @RequestBody UpdateQuantityRequest request
    ) {
        OrderId orderIdObj = OrderId.from(orderId);
        UpdateQuantityCommand command = mapper.toCommand(request);
        
        updateQuantityUseCase.execute(orderIdObj, itemId, command);
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK.value(), "Cantidad actualizada exitosamente")
        );
    }
    
    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse<String>> removeItem(
        @PathVariable String orderId,
        @PathVariable Long itemId
    ) {
        OrderId orderIdObj = OrderId.from(orderId);
        
        removeItemUseCase.execute(orderIdObj, itemId);
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK.value(), "Item eliminado exitosamente")
        );
    }
}
