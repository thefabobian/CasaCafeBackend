package com.casacafemonteria.customer.factory;

import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import com.casacafemonteria.customer.presentation.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerFactory {
    private final ModelMapper modelMapper;

    public CustomerDto customerDto(CustomerEntity customerEntity){
        return CustomerDto.builder()
                .uuid(customerEntity.getUuid())
                .dni(customerEntity.getDni())
                .phone(customerEntity.getPhone())
                .address(customerEntity.getAddress())
                .birth(customerEntity.getBirth())
                .username(customerEntity.getUserEntity().getUsername())
                .email(customerEntity.getUserEntity().getEmail())
                .createdAt(customerEntity.getCreatedAt())
                .build();
    }
}
