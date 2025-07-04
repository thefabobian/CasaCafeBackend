package com.casacafemonteria.cart.service.implementation;

import com.casacafemonteria.cart.factory.CartFactory;
import com.casacafemonteria.cart.persistence.entities.CartEntity;
import com.casacafemonteria.cart.persistence.repositories.CartRepository;
import com.casacafemonteria.cart.presentation.dto.CartDto;
import com.casacafemonteria.cart.presentation.payload.CartPayload;
import com.casacafemonteria.cart.service.interfaces.ICartService;
import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import com.casacafemonteria.customer.persistence.repositories.CustomerRepository;
import com.casacafemonteria.helpers.exception.exceptions.ConflictException;
import com.casacafemonteria.helpers.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CartFactory cartFactory;

    @Override
    @Transactional
    public CartDto create(CartPayload cartPayload) {
        CustomerEntity customerEntity = customerRepository.findByUuid(cartPayload.getCustomerUuid())
                .orElseThrow(()-> new ResourceNotFoundException("El cliente con el uuid " + cartPayload.getCustomerUuid() + " no existe"));

        if (cartRepository.findByCustomerEntity(customerEntity).isPresent()) {
            throw new ConflictException("El cliente ya tiene un carrito creado");
        }

        CartEntity cartEntity = cartFactory.cartEntity(customerEntity);
        return cartFactory.cartDto(cartRepository.save(cartEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public CartDto getByCustomerUuid(UUID uuid) {
        CustomerEntity customerEntity = customerRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para UUID: " + uuid));

        CartEntity cart = cartRepository.findByCustomerEntity(customerEntity)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para cliente: " + customerEntity));

        // La lista items ya viene cargada por LAZY fetch o haz fetch explícito si usas LAZY
        Hibernate.initialize(cart.getItems()); // fuerza carga si es LAZY

        return cartFactory.cartDto(cart);
    }

}
