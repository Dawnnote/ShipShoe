package com.hanghae.shipshoe.domain.item.entity;

import com.hanghae.shipshoe.domain.BaseEntity;
import com.hanghae.shipshoe.domain.item.dto.ItemRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stock;

    public void createItem(ItemRequestDto request) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.stock = request.getStock();
    }

    // 재고 감소
    public void removeStock(int countStock) {
        this.stock -= countStock;
    }

    // 재고 증가
    public void addStock(int orderCount) {
        this.stock += orderCount;
    }
}
