package com.casacafemonteria.product.persistence.repositories;

import com.casacafemonteria.product.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findByNameContainingIgnoreCase(String name);
}
