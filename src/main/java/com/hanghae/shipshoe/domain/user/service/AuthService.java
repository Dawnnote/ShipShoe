package com.hanghae.shipshoe.domain.user.service;


import com.hanghae.shipshoe.domain.user.dto.auth.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
    ResponseEntity<? super SignUpResponseDto> signUp (@Valid SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> logIn (@Valid SignInRequestDto dto);
}
