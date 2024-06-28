package com.hanghae.shipshoe.domain.user.dto.auth;

import com.hanghae.shipshoe.domain.user.common.ResponseCode;
import com.hanghae.shipshoe.domain.user.common.ResponseMessage;
import com.hanghae.shipshoe.domain.user.dto.UserResponseValidationDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignUpResponseDto extends UserResponseValidationDto {

    public SignUpResponseDto() {
        super();
    }

    public static ResponseEntity<SignUpResponseDto> success() {
        SignUpResponseDto responseBody = new SignUpResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<UserResponseValidationDto> duplicatedId() {
        UserResponseValidationDto responseBody = new UserResponseValidationDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<UserResponseValidationDto> certificationFail() {
        UserResponseValidationDto responseBody = new UserResponseValidationDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

}
