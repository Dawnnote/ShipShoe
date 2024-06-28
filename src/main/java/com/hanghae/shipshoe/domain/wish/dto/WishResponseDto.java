package com.hanghae.shipshoe.domain.wish.dto;

import com.hanghae.shipshoe.domain.wish.entity.Wish;
import lombok.Data;

@Data
public class WishResponseDto {
    private String name;
    private int price;

    public WishResponseDto(Wish wish) {
        this.name = wish.getItem().getName();
        this.price = wish.getItem().getPrice();
    }
}
