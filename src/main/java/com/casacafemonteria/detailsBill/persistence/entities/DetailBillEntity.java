package com.casacafemonteria.detailsBill.persistence.entities;

import com.casacafemonteria.bill.persistence.entities.BillEntity;
import com.casacafemonteria.product.persistence.entities.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "details_bill")
public class DetailBillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bill_id", nullable = false)
    private BillEntity billEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double priceUnitary;
}
