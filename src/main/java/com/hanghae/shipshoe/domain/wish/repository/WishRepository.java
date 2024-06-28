package com.hanghae.shipshoe.domain.wish.repository;

import com.hanghae.shipshoe.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByUserId(Long userId);
}
