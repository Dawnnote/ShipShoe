package com.hanghae.shipshoe.domain.delivery.dto;

import com.hanghae.shipshoe.domain.Address;
import com.hanghae.shipshoe.domain.delivery.entity.Delivery;
import com.hanghae.shipshoe.domain.delivery.entity.DeliveryStatus;

import lombok.Data;

@Data
public class DeliveryDto {

    private Long id;
    private Address address;
    private DeliveryStatus status;

    public DeliveryDto() {
    }

    public DeliveryDto(Delivery delivery) {
        this.id = delivery.getId();
        this.address = delivery.getAddress();
        this.status = delivery.getStatus();
    }
}
