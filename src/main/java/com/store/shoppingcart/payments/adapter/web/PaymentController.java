package com.store.shoppingcart.payments.adapter.web;

import com.store.shoppingcart.common.dto.ApiResponse;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.payments.adapter.web.dto.PaymentDtoMapper;
import com.store.shoppingcart.payments.adapter.web.dto.PaymentRequestDto;
import com.store.shoppingcart.payments.adapter.web.dto.PaymentResponseDto;
import com.store.shoppingcart.payments.application.port.in.FindPaymentUseCase;
import com.store.shoppingcart.payments.application.port.in.ProcessPaymentUseCase;
import com.store.shoppingcart.payments.application.port.in.RefundPaymentUseCase;
import com.store.shoppingcart.payments.domain.model.Payment;
import com.store.shoppingcart.payments.domain.model.PaymentId;
import com.store.shoppingcart.payments.domain.strategy.PaymentRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    private final ProcessPaymentUseCase processPaymentUseCase;
    private final FindPaymentUseCase findPaymentUseCase;
    private final RefundPaymentUseCase refundPaymentUseCase;
    private final OrderRepository orderRepository;
    private final PaymentDtoMapper mapper;
    
    public PaymentController(
            ProcessPaymentUseCase processPaymentUseCase,
            FindPaymentUseCase findPaymentUseCase,
            RefundPaymentUseCase refundPaymentUseCase,
            OrderRepository orderRepository,
            PaymentDtoMapper mapper) {
        this.processPaymentUseCase = processPaymentUseCase;
        this.findPaymentUseCase = findPaymentUseCase;
        this.refundPaymentUseCase = refundPaymentUseCase;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }
    
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<PaymentResponseDto>> processPayment(
            @PathVariable String orderId,
            @Valid @RequestBody PaymentRequestDto requestDto) {
        
        OrderId orderIdObj = OrderId.from(orderId);
        Order order = orderRepository.findById(orderIdObj)
            .orElseThrow(() -> new OrderNotFoundException(orderIdObj));
        
        PaymentRequest paymentRequest = mapper.toDomainRequest(requestDto, order.getTotalAmount());
        Payment payment = processPaymentUseCase.processPayment(orderIdObj, paymentRequest);
        PaymentResponseDto responseDto = mapper.toDto(payment);
        
        ApiResponse<PaymentResponseDto> response = ApiResponse.success(
            HttpStatus.CREATED.value(),
            responseDto
        );
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponseDto>> getPayment(@PathVariable String paymentId) {
        PaymentId id = PaymentId.from(paymentId);
        Payment payment = findPaymentUseCase.findById(id);
        PaymentResponseDto responseDto = mapper.toDto(payment);
        
        ApiResponse<PaymentResponseDto> response = ApiResponse.success(
            HttpStatus.OK.value(),
            responseDto
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<List<PaymentResponseDto>>> getPaymentsByOrder(
            @PathVariable String orderId) {
        
        OrderId orderIdObj = OrderId.from(orderId);
        List<Payment> payments = findPaymentUseCase.findByOrderId(orderIdObj);
        List<PaymentResponseDto> responseDtos = payments.stream()
            .map(mapper::toDto)
            .toList();
        
        ApiResponse<List<PaymentResponseDto>> response = ApiResponse.success(
            HttpStatus.OK.value(),
            responseDtos
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<ApiResponse<String>> refundPayment(@PathVariable String paymentId) {
        PaymentId id = PaymentId.from(paymentId);
        refundPaymentUseCase.refundPayment(id);
        
        ApiResponse<String> response = ApiResponse.success(
            HttpStatus.OK.value(),
            "Reembolso procesado exitosamente"
        );
        
        return ResponseEntity.ok(response);
    }
}