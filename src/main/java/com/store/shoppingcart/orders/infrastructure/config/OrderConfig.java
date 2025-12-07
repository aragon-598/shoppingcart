package com.store.shoppingcart.orders.infrastructure.config;

import com.store.shoppingcart.orders.domain.factory.OrderFactory;
import com.store.shoppingcart.orders.domain.factory.OrderFactoryImpl;
import com.store.shoppingcart.orders.domain.port.out.ClientValidatorPort;
import com.store.shoppingcart.products.domain.port.out.ProductProviderPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {
    
    @Bean
    public OrderFactory orderFactory(ClientValidatorPort clientValidator, 
                                     ProductProviderPort productProvider) {
        return new OrderFactoryImpl(clientValidator, productProvider);
    }
}
