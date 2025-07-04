package com.casacafemonteria.itemCart.presentation.dto;

import com.casacafemonteria.product.presentation.dto.ProductDto;
import lombok.Builder;

@Builder
public record ItemCartDto(
        Long id,
        Long cartId,
        ProductDto product,
        Integer quantity
) {
}
