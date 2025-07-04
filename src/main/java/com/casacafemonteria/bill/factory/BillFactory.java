package com.casacafemonteria.bill.factory;

import com.casacafemonteria.bill.persistence.entities.BillEntity;
import com.casacafemonteria.bill.presentation.dto.BillDto;
import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BillFactory {

    public BillDto billDto(BillEntity entity) {
        return BillDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .customerUuid(entity.getCustomerEntity().getUuid())
                .build();
    }

    public BillEntity billEntity(CustomerEntity customer) {
        return BillEntity.builder()
                .date(LocalDate.now())
                .customerEntity(customer)
                .build();
    }
}

