package com.hanghae.shipshoe.domain.order.api;

import com.hanghae.shipshoe.domain.delivery.dto.DeliveryDto;
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

    // 주문 생성
    @PostMapping("/api/order")
    public OrderResponse order(@RequestBody OrderRequest request) {
        return orderService.order(request);
    }

    // ORDER 상태의 주문 전체 조회
    @GetMapping("/api/user/{userId}/orders")
    public Page<OrderResponse> getOrders(@PathVariable("userId") Long userId,
                                         @PageableDefault(size = 10) Pageable pageable) {
        return orderService.findOrders(userId, pageable);
    }

    // 취소 상품 전체 조회
    @GetMapping("/api/user/{userId}/orders/cancel")
    public Page<OrderResponse> getCancelOrders(@PathVariable("userId") Long userId,
                                               @PageableDefault(size = 10) Pageable pageable) {
        return orderService.findCancelOrder(userId, pageable);
    }

    // 반품 상품 전체 조회
    @GetMapping("/api/user/{userId}/orders/return")
    public Page<OrderResponse> getReturnOrders(@PathVariable("userId") Long userId,
                                               @PageableDefault(size = 10) Pageable pageable) {
        return orderService.findReturnOrder(userId, pageable);
    }

    // 주문 상품 취소
    @PostMapping("/api/order/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
    }

    // 주문 상품 반품
    @PostMapping("api/order/{orderId}/return")
    public void returnOrder(@PathVariable("orderId") Long orderId) {
        orderService.returnOrder(orderId);
    }

    // 주문 상품 배송 조회
    @GetMapping("api/order/{orderId}/delivery")
    public DeliveryDto getDelivery(@PathVariable("orderId") Long orderId) {
        return orderService.getDelivery(orderId);
    }
}
