package com.hanghae.shipshoe.domain.order.service;

import com.hanghae.shipshoe.domain.delivery.entity.Delivery;
import com.hanghae.shipshoe.domain.item.entity.Item;
import com.hanghae.shipshoe.domain.item.repository.ItemRepository;
import com.hanghae.shipshoe.domain.order.dto.OrderRequest;
import com.hanghae.shipshoe.domain.order.dto.OrderResponse;
import com.hanghae.shipshoe.domain.order.entity.Order;
import com.hanghae.shipshoe.domain.order.repository.OrderRepository;
import com.hanghae.shipshoe.domain.order_line.entity.OrderLine;
import com.hanghae.shipshoe.domain.user.entity.User;
import com.hanghae.shipshoe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

//    private final ScheduledExecutorService scheduledExecutorService;

    // 주문 생성
    @Transactional
    public OrderResponse order(OrderRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow();
        Item item = itemRepository.findById(request.getItemId()).orElseThrow();
        Delivery delivery = new Delivery(user.getAddress());
        OrderLine orderLine = OrderLine.createOrderLine(item, item.getPrice(), request.getCount());
        Order order = Order.createOrder(user, delivery, orderLine);

//        Order saveOrder = orderRepository.save(order);
//        scheduleStatusUpdate(saveOrder, 5, DeliveryStatus.SHIP);
//        scheduleStatusUpdate(saveOrder, 10, DeliveryStatus.COMP);

        orderRepository.save(order);
        return new OrderResponse(order);
    }

    // 주문 상태가 ORDER 인 것들만 보여줌
    public Page<OrderResponse> findOrders(Long id, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findOrderUser(id, pageable);
        return orderPage.map(OrderResponse::new);
    }


    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.cancel();
    }

    // 상품 반품
    @Transactional
    public void returnOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.back();
    }




    // 배송 상태 스케줄,,,,,
//    private void scheduleStatusUpdate(Order order, long delayInDays, DeliveryStatus status) {
//        long delayInMillis = TimeUnit.SECONDS.toMillis(delayInDays);
//        scheduledExecutorService.schedule(() -> {
//            order.getDelivery().setStatus(status);
//            orderRepository.save(order);
//        }, delayInMillis, TimeUnit.MILLISECONDS);
//    }
}
