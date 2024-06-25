package com.hanghae.shipshoe.domain.delivery.service;

import com.hanghae.shipshoe.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryStatusScheduler {

    private final OrderService orderService;

    @Scheduled(cron = "0/5 * * * * *")
    public void updateDeliveryStatus() {
        orderService.updateOrderDeliveryStatus();
        log.info("Schedule Counter");
    }
}
