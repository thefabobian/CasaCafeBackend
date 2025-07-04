package com.casacafemonteria.customer.persistence.entities;

import com.casacafemonteria.cart.persistence.entities.CartEntity;
import com.casacafemonteria.user.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class CustomerEntity {

    @Id
    private Long id;

    @MapsId
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private UserEntity userEntity;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String dni;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date birth;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    // Nueva relacion con CartEntity
    @OneToOne(mappedBy = "customerEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CartEntity cartEntity;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null){
            this.uuid = userEntity.getUuid();
        }
        this.createdAt = new Date();
    }
}
