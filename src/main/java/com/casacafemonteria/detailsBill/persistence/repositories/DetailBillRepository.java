package com.casacafemonteria.detailsBill.persistence.repositories;

import com.casacafemonteria.bill.persistence.entities.BillEntity;
import com.casacafemonteria.detailsBill.persistence.entities.DetailBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailBillRepository extends JpaRepository<DetailBillEntity, Long> {
    List<DetailBillEntity> findByBillEntity(BillEntity billEntity);
}
