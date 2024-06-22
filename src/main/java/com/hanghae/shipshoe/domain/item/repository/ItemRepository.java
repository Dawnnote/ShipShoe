package com.hanghae.shipshoe.domain.item.repository;

import com.hanghae.shipshoe.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
