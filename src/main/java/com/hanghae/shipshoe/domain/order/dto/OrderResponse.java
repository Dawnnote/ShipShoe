package com.hanghae.shipshoe.domain.order.dto;

import com.hanghae.shipshoe.domain.Address;
import com.hanghae.shipshoe.domain.delivery.dto.DeliveryDto;
import com.hanghae.shipshoe.domain.delivery.entity.Delivery;
import com.hanghae.shipshoe.domain.order.entity.Order;
import com.hanghae.shipshoe.domain.order.entity.OrderStatus;
import com.hanghae.shipshoe.domain.order_line.dto.OrderLineDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private Long userId;
    private DeliveryDto delivery;
    private List<OrderLineDto> orderLines;
    private OrderStatus status;
    private LocalDateTime orderDate;


    public OrderResponse(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        delivery = new DeliveryDto(order.getDelivery());
        this.orderLines = order.getOrderLines().stream()
                .map(OrderLineDto::new)
                .toList();
        this.status = order.getStatus();
        this.orderDate = order.getCreatedAt();
    }
}
