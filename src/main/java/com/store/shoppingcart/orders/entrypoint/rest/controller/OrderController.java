package com.store.shoppingcart.orders.entrypoint.rest.controller;

import com.store.shoppingcart.common.dto.ApiResponse;
import com.store.shoppingcart.orders.application.dto.CreateOrderCommand;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.port.in.CancelOrderUseCase;
import com.store.shoppingcart.orders.domain.port.in.CreateOrderUseCase;
import com.store.shoppingcart.orders.domain.port.in.FindOrderUseCase;
import com.store.shoppingcart.orders.domain.port.in.UpdateOrderStatusUseCase;
import com.store.shoppingcart.orders.entrypoint.rest.dto.CreateOrderRequest;
import com.store.shoppingcart.orders.entrypoint.rest.dto.OrderResponse;
import com.store.shoppingcart.orders.entrypoint.rest.mapper.OrderDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final CreateOrderUseCase createOrderUseCase;
    private final FindOrderUseCase findOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final OrderDtoMapper mapper;
    
    public OrderController(CreateOrderUseCase createOrderUseCase, FindOrderUseCase findOrderUseCase,
                           UpdateOrderStatusUseCase updateOrderStatusUseCase, CancelOrderUseCase cancelOrderUseCase,
                           OrderDtoMapper mapper) {
        this.createOrderUseCase = createOrderUseCase;
        this.findOrderUseCase = findOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
        this.mapper = mapper;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CreateOrderCommand command = mapper.toCommand(request);
        OrderId orderId = createOrderUseCase.execute(command);
        
        Order order = findOrderUseCase.execute(orderId);
        OrderResponse response = mapper.toResponse(order);
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED.value(), response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable String id) {
        OrderId orderId = OrderId.from(id);
        Order order = findOrderUseCase.execute(orderId);
        
        OrderResponse response = mapper.toResponse(order);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), response));
    }
    
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<String>> confirmOrder(@PathVariable String id) {
        OrderId orderId = OrderId.from(id);
        updateOrderStatusUseCase.confirm(orderId);
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK.value(), "Order confirmed successfully")
        );
    }
    
    @PatchMapping("/{id}/ship")
    public ResponseEntity<ApiResponse<String>> shipOrder(@PathVariable String id) {
        OrderId orderId = OrderId.from(id);
        updateOrderStatusUseCase.ship(orderId);
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK.value(), "Order shipped successfully")
        );
    }
    
    @PatchMapping("/{id}/deliver")
    public ResponseEntity<ApiResponse<String>> deliverOrder(@PathVariable String id) {
        OrderId orderId = OrderId.from(id);
        updateOrderStatusUseCase.deliver(orderId);
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK.value(), "Order delivered successfully")
        );
    }
    
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelOrder(@PathVariable String id) {
        OrderId orderId = OrderId.from(id);
        cancelOrderUseCase.execute(orderId);
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK.value(), "Order cancelled successfully")
        );
    }
}
