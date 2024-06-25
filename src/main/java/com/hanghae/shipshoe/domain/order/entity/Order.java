package com.hanghae.shipshoe.domain.order.entity;

import com.hanghae.shipshoe.domain.BaseEntity;
import com.hanghae.shipshoe.domain.delivery.entity.Delivery;
import com.hanghae.shipshoe.domain.delivery.entity.DeliveryStatus;
import com.hanghae.shipshoe.domain.order_line.entity.OrderLine;
import com.hanghae.shipshoe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@ToString
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    protected Order() {}

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setOrder(this);
    }

    private void setOrder(User user, Delivery delivery) {
        this.user = user;
        this.delivery = delivery;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public static Order createOrder(User user, Delivery delivery, OrderLine... orderLines) {
        Order order = new Order();
        order.setOrder(user, delivery);
        for (OrderLine orderLine : orderLines) {
            order.addOrderLine(orderLine);
        }
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.SHIP ||
                delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("상품 취소 불가능");
        }
        this.setStatus(OrderStatus.CANCEL);
        this.delivery.setStatus(DeliveryStatus.CANCEL);
        for (OrderLine orderLine : orderLines) {
            orderLine.cancel();
        }
    }

    public void updateDeliveryStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (getCreatedAt().plusSeconds(10).isBefore(now) && delivery.getStatus() == DeliveryStatus.READY) {
            delivery.setStatus(DeliveryStatus.SHIP);
        } else if (getCreatedAt().plusSeconds(20).isBefore(now) && delivery.getStatus() == DeliveryStatus.SHIP) {
            delivery.setStatus(DeliveryStatus.COMP);
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderLine orderLine : orderLines) {
            totalPrice += orderLine.getTotalPrice();
        }
        return totalPrice;
    }
}
