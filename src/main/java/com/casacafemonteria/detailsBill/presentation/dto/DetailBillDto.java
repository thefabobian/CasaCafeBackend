package com.casacafemonteria.detailsBill.presentation.dto;

import lombok.Builder;

@Builder
public record DetailBillDto(
        Long id,
        Long billId,
        Long productId,
        String productName,
        Double priceUnitary,
        Integer quantity
) {
}
