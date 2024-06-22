package com.hanghae.shipshoe.domain.user.entity;

import com.hanghae.shipshoe.domain.Address;
import com.hanghae.shipshoe.domain.BaseEntity;
import com.hanghae.shipshoe.domain.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;

    @Embedded
    private Address address;

    private String phone;

    @Enumerated(value = EnumType.STRING)
    private RoleType role;

    protected User() {
    }

    public User(UserRequestDto request, Address address) {
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.name = request.getName();
        this.address = address;
        this.phone = request.getPhone();
        this.role = RoleType.USER;
    }
}
