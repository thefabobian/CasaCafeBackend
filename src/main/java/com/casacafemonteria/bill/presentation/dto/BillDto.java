package com.casacafemonteria.bill.presentation.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record BillDto(
        Long id,
        LocalDate date,
        UUID customerUuid
) {
}
