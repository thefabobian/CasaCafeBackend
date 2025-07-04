package com.casacafemonteria.cart.factory;

import com.casacafemonteria.cart.persistence.entities.CartEntity;
import com.casacafemonteria.cart.presentation.dto.CartDto;
import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import com.casacafemonteria.itemCart.factory.ItemCartFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CartFactory {

    private final ItemCartFactory itemCartFactory;

    public CartDto cartDto(CartEntity entity) {
        return CartDto.builder()
                .id(entity.getId())
                .customerUuid(entity.getCustomerEntity().getUuid())
                .items(entity.getItems() == null
                        ? Collections.emptyList()
                        : entity.getItems().stream()
                        .map(itemCartFactory::itemCartDto)
                        .toList())
                .build();
    }

    public CartEntity cartEntity(CustomerEntity customerEntity) {
        return CartEntity.builder()
                .customerEntity(customerEntity)
                .build();
    }
}
