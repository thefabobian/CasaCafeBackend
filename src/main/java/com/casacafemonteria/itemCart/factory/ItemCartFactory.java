package com.casacafemonteria.itemCart.factory;

import com.casacafemonteria.itemCart.persistence.entities.ItemCartEntity;
import com.casacafemonteria.itemCart.presentation.dto.ItemCartDto;
import com.casacafemonteria.product.persistence.entities.ProductEntity;
import com.casacafemonteria.product.presentation.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemCartFactory {

    public ItemCartDto itemCartDto(ItemCartEntity itemCartEntity){
        ProductEntity product = itemCartEntity.getProductEntity();

        ProductDto productDto = ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .build();

        return ItemCartDto.builder()
                .id(itemCartEntity.getId())
                .product(productDto)
                .quantity(itemCartEntity.getQuantity())
                .build();
    }
}
