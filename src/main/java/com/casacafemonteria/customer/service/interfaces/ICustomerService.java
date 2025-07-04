package com.casacafemonteria.customer.service.interfaces;

import com.casacafemonteria.customer.presentation.dto.CustomerDto;
import com.casacafemonteria.customer.presentation.payload.CustomerPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ICustomerService {
    void save(CustomerPayload customerPayload);
    void update(CustomerPayload customerPayload, UUID uuid);
    void deleteByUuid(UUID uuid);
    Page<CustomerDto> findByDni(String dni, Pageable pageable);
    Page<CustomerDto> findAll(Pageable pageable);
    CustomerDto findByUuid(UUID uuid);
}
