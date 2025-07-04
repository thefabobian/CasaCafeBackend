package com.casacafemonteria.bill.presentation.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillPayload {

    @NotNull(message = "El UUID del cliente es obligatorio")
    private UUID customerUuid;
}
