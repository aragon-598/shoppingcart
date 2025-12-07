package com.store.shoppingcart.orders.application.usecase;

import com.store.shoppingcart.orders.application.dto.AddItemCommand;
import com.store.shoppingcart.orders.domain.exception.OrderNotFoundException;
import com.store.shoppingcart.orders.domain.exception.OrderNotModifiableException;
import com.store.shoppingcart.orders.domain.model.Money;
import com.store.shoppingcart.orders.domain.model.Order;
import com.store.shoppingcart.orders.domain.model.OrderId;
import com.store.shoppingcart.orders.domain.model.OrderItem;
import com.store.shoppingcart.orders.domain.port.in.AddItemToOrderUseCase;
import com.store.shoppingcart.orders.domain.port.out.OrderRepository;
import com.store.shoppingcart.products.domain.model.Product;
import com.store.shoppingcart.products.domain.port.out.ProductProviderPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddItemToOrderUseCaseImpl implements AddItemToOrderUseCase {
    
    private final OrderRepository orderRepository;
    private final ProductProviderPort productProvider;
    
    public AddItemToOrderUseCaseImpl(OrderRepository orderRepository, ProductProviderPort productProvider) {
        this.orderRepository = orderRepository;
        this.productProvider = productProvider;
    }
    
    @Override
    @Transactional
    public void execute(OrderId orderId, AddItemCommand command) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        if (!order.isModifiable()) {
            throw new OrderNotModifiableException(orderId);
        }
        
        Product product = productProvider.findById(command.productId());
        Money unitPrice = new Money(product.getPrice().amount(), product.getPrice().currency());
        
        OrderItem newItem = new OrderItem(
            command.productId(),
            product.getTitle(),
            unitPrice,
            command.quantity()
        );
        
        order.addItem(newItem);
        orderRepository.save(order);
    }
}
