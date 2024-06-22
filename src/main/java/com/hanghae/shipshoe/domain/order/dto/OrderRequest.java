package com.hanghae.shipshoe.domain.order.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private Long itemId;
    private int count;
}
