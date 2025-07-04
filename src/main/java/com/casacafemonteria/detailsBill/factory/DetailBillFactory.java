package com.casacafemonteria.detailsBill.factory;

import com.casacafemonteria.detailsBill.persistence.entities.DetailBillEntity;
import com.casacafemonteria.detailsBill.presentation.dto.DetailBillDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetailBillFactory {
    public DetailBillDto detailBillEntity(DetailBillEntity detailBillEntity) {
        return DetailBillDto.builder()
                .id(detailBillEntity.getId())
                .billId(detailBillEntity.getBillEntity().getId())
                .productId(detailBillEntity.getProductEntity().getId())
                .productName(detailBillEntity.getProductEntity().getName())
                .priceUnitary(detailBillEntity.getPriceUnitary())
                .quantity(detailBillEntity.getQuantity())
                .build();
    }
}
