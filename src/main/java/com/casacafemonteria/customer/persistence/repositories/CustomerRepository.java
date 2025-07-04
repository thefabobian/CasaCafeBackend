package com.casacafemonteria.customer.persistence.repositories;

import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import com.casacafemonteria.user.persistence.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByUuid(UUID uuid);

    Optional<CustomerEntity> findByUserEntity(UserEntity userEntity);

    Page<CustomerEntity> findByDni(String dni, Pageable pageable);

}
