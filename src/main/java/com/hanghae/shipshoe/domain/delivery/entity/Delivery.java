package com.hanghae.shipshoe.domain.delivery.entity;

import com.hanghae.shipshoe.domain.Address;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    protected Delivery(){}

    public Delivery(Address address) {
        this.address = address;
        this.status = DeliveryStatus.READY;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}
