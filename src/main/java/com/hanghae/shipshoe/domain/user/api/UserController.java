package com.hanghae.shipshoe.domain.user.api;

import com.hanghae.shipshoe.domain.user.dto.UserRequestDto;
import com.hanghae.shipshoe.domain.user.dto.UserResponseDto;
import com.hanghae.shipshoe.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 회원가입
    @PostMapping("/api/users/signup")
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto request) {
        return userService.save(request);
    }

    // 유저 프로필
    @GetMapping("/api/users/{id}")
    public UserResponseDto getUser(@PathVariable("id") Long id) {
        return userService.findUser(id);
    }
}
