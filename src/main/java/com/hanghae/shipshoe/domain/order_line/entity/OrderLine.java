package com.hanghae.shipshoe.domain.order_line.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.shipshoe.domain.item.entity.Item;
import com.hanghae.shipshoe.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class OrderLine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int orderCount;

    protected OrderLine() {}

    private void setOrderLine(Item item, int orderPrice, int orderCount) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static OrderLine createOrderLine(Item item, int price, int count) {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrderLine(item, price, count);
        item.removeStock(count);
        return orderLine;
    }

    public void cancel() {
        getItem().addStock(orderCount);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getOrderCount();
    }
}
