package com.hanghae.shipshoe.domain.order.api;

import com.hanghae.shipshoe.domain.order.dto.OrderRequest;
import com.hanghae.shipshoe.domain.order.dto.OrderResponse;
import com.hanghae.shipshoe.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public OrderResponse order(@RequestBody OrderRequest request) {
        return orderService.order(request);
    }

    @GetMapping("/api/user/{userId}/orders")
    public Page<OrderResponse> getOrders(@PathVariable("userId") Long userId,
                                         @PageableDefault(size = 3) Pageable pageable) {
        return orderService.findOrders(userId, pageable);
    }

    @PostMapping("/api/order/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
    }
}
