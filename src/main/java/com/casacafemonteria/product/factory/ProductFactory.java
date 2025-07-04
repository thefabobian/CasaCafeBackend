package com.casacafemonteria.product.factory;

import com.casacafemonteria.product.persistence.entities.ProductEntity;
import com.casacafemonteria.product.presentation.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ProductFactory {
    private final ModelMapper modelMapper;

    public ProductDto productDto(ProductEntity productEntity) {

        return ProductDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .imageUrl(productEntity.getImageUrl())
                .build();
    }
}
