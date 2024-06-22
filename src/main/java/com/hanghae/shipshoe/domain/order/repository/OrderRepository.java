package com.hanghae.shipshoe.domain.order.repository;

import com.hanghae.shipshoe.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join o.user u where u.id = :id and o.status = 'ORDER'")
    Page<Order> findOrderUser(@Param("id") Long id, Pageable pageable);
}