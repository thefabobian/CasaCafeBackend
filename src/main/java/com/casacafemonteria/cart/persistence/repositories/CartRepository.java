package com.casacafemonteria.cart.persistence.repositories;

import com.casacafemonteria.cart.persistence.entities.CartEntity;
import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByCustomerEntity(CustomerEntity customerEntity);

}
