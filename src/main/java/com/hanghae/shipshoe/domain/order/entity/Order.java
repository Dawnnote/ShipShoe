package com.hanghae.shipshoe.domain.order.entity;

import com.hanghae.shipshoe.domain.BaseEntity;
import com.hanghae.shipshoe.domain.delivery.entity.Delivery;
import com.hanghae.shipshoe.domain.delivery.entity.DeliveryStatus;
import com.hanghae.shipshoe.domain.order_line.entity.OrderLine;
import com.hanghae.shipshoe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter
@ToString
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setUser(User user) {
        this.user = user;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setOrder(this);
    }

    protected Order(){}

    public static Order createOrder(User user, Delivery delivery, OrderLine... orderLines) {
        Order order = new Order();
        order.setUser(user);
        order.setDelivery(delivery);
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
        for (OrderLine orderLine : orderLines) {
            orderLine.cancel();
        }
    }

    public void back() {
        if (delivery.getStatus() == DeliveryStatus.READY ||
            delivery.getStatus() == DeliveryStatus.SHIP ||
            getStatus() == OrderStatus.CANCEL) {
            throw new IllegalStateException("상품 반품 불가능");
        }
        this.setStatus(OrderStatus.RETURN);
        for (OrderLine orderLine : orderLines) {
            orderLine.back();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderLine orderLine: orderLines) {
            totalPrice += orderLine.getTotalPrice();
        }

        return totalPrice;
    }
}
