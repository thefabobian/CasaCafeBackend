package com.casacafemonteria.detailsBill.service.interfaces;

import com.casacafemonteria.detailsBill.presentation.dto.DetailBillDto;
import com.casacafemonteria.detailsBill.presentation.payload.DetailBillPayload;

import java.util.List;

public interface IDetailBillService {
    DetailBillDto create(DetailBillPayload payload);
    List<DetailBillDto> getByBillId(Long billId);
    DetailBillDto findById(Long id);
    void delete(Long id);
}
