package com.hanghae.shipshoe.domain.item.dto;

import com.hanghae.shipshoe.domain.item.entity.Item;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class ItemResponseDto {

    private Long itemId;
    private String name;
    private int price;
    private int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ItemResponseDto(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stock = item.getStock();
        this.createdAt = item.getCreatedAt();
        this.updatedAt = item.getUpdatedAt();
    }
}
