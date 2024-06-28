package com.hanghae.shipshoe.domain.wish.controller;

import com.hanghae.shipshoe.domain.wish.dto.WishRequestDto;
import com.hanghae.shipshoe.domain.wish.dto.WishResponseDto;
import com.hanghae.shipshoe.domain.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    // 위시리스트 생성
    @PostMapping("/api/users/wish")
    public WishResponseDto wish(@RequestBody WishRequestDto request) {
        return wishService.wish(request);
    }

    // 위시리스트 조회
    @GetMapping("/api/users/{userId}/wish")
    public List<WishResponseDto> getWishList(@PathVariable("userId") Long userId) {
        return wishService.getWish(userId);
    }

    // 위시리스트 삭제
    @DeleteMapping("/api/wish/{wishId}/delete")
    public void deleteWish(@PathVariable("wishId") Long wishId) {
        wishService.delete(wishId);
    }

}
