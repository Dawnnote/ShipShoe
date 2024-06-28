package com.hanghae.shipshoe.domain.user.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
