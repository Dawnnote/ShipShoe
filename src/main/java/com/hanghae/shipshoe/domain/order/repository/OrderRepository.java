package com.hanghae.shipshoe.domain.order.repository;

import com.hanghae.shipshoe.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join o.user u where u.id = :userId and o.status = 'ORDER'")
    Page<Order> findOrderUser(@Param("userId") Long userId, Pageable pageable);

    @Query("select o from Order o join o.user u where u.id = :userId and o.status = 'CANCEL'")
    Page<Order> findCancelOrder(@Param("userId") Long userId, Pageable pageable);

    @Query("select o from Order o join o.user u where u.id = :userId and o.status = 'RETURN'")
    Page<Order> findReturnOrder(@Param("userId") Long userId, Pageable pageable);

    @Query("select o from Order o join o.delivery d" +
            " where o.status = 'ORDER' and" +
            " d.status = 'READY' or d.status = 'SHIP'")
    List<Order> findByStatus();

    @Query("select o from Order o join o.delivery d" +
            " where o.id = :orderId and d.status = 'READY'")
    Order findReadyStatus(@Param("orderId") Long orderId);

    @Query("select o from Order o" +
            " join fetch o.orderLines ol" +
            " join fetch ol.item" +
            " join fetch o.delivery d" +
            " where o.id = :orderId and d.status = 'COMP'")
    Order findCompStatus(Long orderId);

}