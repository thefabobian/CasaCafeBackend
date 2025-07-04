package com.casacafemonteria.bill.persistence.repositories;

import com.casacafemonteria.bill.persistence.entities.BillEntity;
import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Long> {
    List<BillEntity> findByCustomerEntity(CustomerEntity customerEntity);
}
