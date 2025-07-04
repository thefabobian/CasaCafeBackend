package com.casacafemonteria.product.presentation.dto;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public record ProductDto(
        Long id,
        String name,
        String description,
        Double price,
        Integer stock,
        @Column(name = "image_url")
        String imageUrl
) {}
