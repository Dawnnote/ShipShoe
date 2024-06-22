package com.hanghae.shipshoe.domain.item.dto;

import lombok.Data;

@Data
public class ItemRequestDto {

    private String name;
    private int price;
    private int stock;
}
