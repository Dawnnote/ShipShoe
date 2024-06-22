package com.hanghae.shipshoe.domain.wish.entity;

import com.hanghae.shipshoe.domain.item.entity.Item;
import com.hanghae.shipshoe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Wish {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 수량 변경 및 주문,,,,,


    protected Wish() {
    }
}
