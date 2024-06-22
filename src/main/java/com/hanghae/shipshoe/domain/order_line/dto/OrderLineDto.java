package com.hanghae.shipshoe.domain.order_line.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.shipshoe.domain.item.entity.Item;
import com.hanghae.shipshoe.domain.order.entity.Order;
import com.hanghae.shipshoe.domain.order_line.entity.OrderLine;
import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.FetchType.LAZY;

@Data
public class OrderLineDto {
    private Long id;
    private String itemName;
    private int quantity;
    private int price;

    public OrderLineDto(OrderLine orderLine) {
        this.id = orderLine.getId();
        this.itemName = orderLine.getItem().getName();
        this.quantity = orderLine.getOrderCount();
        this.price = orderLine.getOrderPrice();
    }
}
