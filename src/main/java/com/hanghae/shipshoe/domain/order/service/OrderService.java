package com.hanghae.shipshoe.domain.order.service;

import com.hanghae.shipshoe.domain.delivery.entity.Delivery;
import com.hanghae.shipshoe.domain.delivery.entity.DeliveryStatus;
import com.hanghae.shipshoe.domain.item.entity.Item;
import com.hanghae.shipshoe.domain.item.repository.ItemRepository;
import com.hanghae.shipshoe.domain.order.dto.OrderRequest;
import com.hanghae.shipshoe.domain.order.dto.OrderResponse;
import com.hanghae.shipshoe.domain.order.entity.Order;
import com.hanghae.shipshoe.domain.order.entity.OrderStatus;
import com.hanghae.shipshoe.domain.order.repository.OrderRepository;
import com.hanghae.shipshoe.domain.order_line.entity.OrderLine;
import com.hanghae.shipshoe.domain.user.entity.User;
import com.hanghae.shipshoe.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    private ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void init() {
        scheduledExecutorService = Executors.newScheduledThreadPool(5);
    }

    // 주문 생성
    @Transactional
    public OrderResponse order(OrderRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow();
        Item item = itemRepository.findById(request.getItemId()).orElseThrow();
        Delivery delivery = new Delivery(user.getAddress());
        OrderLine orderLine = OrderLine.createOrderLine(item, item.getPrice(), request.getCount());
        Order order = Order.createOrder(user, delivery, orderLine);

        orderRepository.save(order);
        return new OrderResponse(order);
    }

    // 주문 상태가 ORDER 인 것들만 보여줌
    public Page<OrderResponse> findOrders(Long userId, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findOrderUser(userId, pageable);
        return orderPage.map(OrderResponse::new);
    }

    // 주문 취소 조회
    public Page<OrderResponse> findCancelOrder(Long userId, Pageable pageable) {
        Page<Order> cancelPage = orderRepository.findCancelOrder(userId, pageable);
        return cancelPage.map(OrderResponse::new);
    }

    // 반품 상품 조회
    public Page<OrderResponse> findReturnOrder(Long userId, Pageable pageable) {
        Page<Order> returnPage = orderRepository.findReturnOrder(userId, pageable);
        return returnPage.map(OrderResponse::new);
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findReadyStatus(orderId);
        order.updateDeliveryStatus();
        order.cancel();
    }

    // 상품 반품
    @Transactional
    public void returnOrder(Long orderId) {
        Order order = orderRepository.findCompStatus(orderId);

        LocalDateTime now = LocalDateTime.now();
        if (order.getDelivery().getStatus() == DeliveryStatus.READY ||
                order.getDelivery().getStatus() == DeliveryStatus.SHIP ||
                order.getStatus() == OrderStatus.CANCEL ||
                order.getCreatedAt().plusSeconds(30).isBefore(now)) {
            throw new IllegalStateException("상품 반품 불가능");
        }

        scheduleStatusUpdate(order);
    }

    // 반품 D+1 후 재고 수량 변경
    public void scheduleStatusUpdate(Order order) {
        long delayInMillis = TimeUnit.SECONDS.toMillis(10);
        log.info("Scheduling status update for order {} in {} milliseconds", order.getId(), delayInMillis);

        scheduledExecutorService.schedule(() -> {
            order.setStatus(OrderStatus.RETURN);
            List<OrderLine> orderLines = order.getOrderLines();
            for (OrderLine orderLine : orderLines) {
                orderLine.cancel();
                itemRepository.save(orderLine.getItem());

                orderRepository.save(order);
                log.info("Order status updated successfully");
            }
        }, delayInMillis, TimeUnit.MILLISECONDS);
    }

    // 배송 상태 스케줄 관리
    @Transactional
    public void updateOrderDeliveryStatus() {
        List<Order> orders = orderRepository.findByStatus();
        for (Order order : orders) {
            order.updateDeliveryStatus();
        }
    }
}
