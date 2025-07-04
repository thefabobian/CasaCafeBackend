package com.casacafemonteria.bill.persistence.entities;

import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import com.casacafemonteria.detailsBill.persistence.entities.DetailBillEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bills")
public class BillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_uuid")
    private CustomerEntity customerEntity;

    @Column(nullable = false)
    private LocalDate date = LocalDate.now();

    @OneToMany(mappedBy = "billEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetailBillEntity> details = new ArrayList<>();
}
