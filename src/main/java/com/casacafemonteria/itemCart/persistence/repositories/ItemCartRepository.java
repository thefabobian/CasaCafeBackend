package com.casacafemonteria.itemCart.persistence.repositories;

import com.casacafemonteria.cart.persistence.entities.CartEntity;
import com.casacafemonteria.itemCart.persistence.entities.ItemCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemCartRepository extends JpaRepository<ItemCartEntity, Long>{
    List<ItemCartEntity> findByCartEntity(CartEntity cartEntity);

    Optional<ItemCartEntity> findByIdAndCartEntity_Id(Long itemId, Long cartId);
}
