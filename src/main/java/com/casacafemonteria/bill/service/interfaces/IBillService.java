package com.casacafemonteria.bill.service.interfaces;

import com.casacafemonteria.bill.presentation.dto.BillDto;
import com.casacafemonteria.bill.presentation.payload.BillPayload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface IBillService {
    BillDto create(BillPayload billPayload);
    List<BillDto> getByCustomerEntity(UUID customerUuid);
    BillDto getById(Long id);
    BillDto generarFacturaDesdeCarrito(UUID customerUuid);
}
