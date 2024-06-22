package com.hanghae.shipshoe.domain.user.dto;
import com.hanghae.shipshoe.domain.Address;
import com.hanghae.shipshoe.domain.user.entity.RoleType;
import com.hanghae.shipshoe.domain.user.entity.User;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private Address address;
    private String phone;
    private RoleType role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.role = user.getRole();
    }
}
