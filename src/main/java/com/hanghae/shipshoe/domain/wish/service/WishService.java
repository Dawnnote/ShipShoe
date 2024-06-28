package com.hanghae.shipshoe.domain.wish.service;

import com.hanghae.shipshoe.domain.item.entity.Item;
import com.hanghae.shipshoe.domain.item.repository.ItemRepository;
import com.hanghae.shipshoe.domain.user.entity.User;
import com.hanghae.shipshoe.domain.user.repository.UserRepository;
import com.hanghae.shipshoe.domain.wish.dto.WishRequestDto;
import com.hanghae.shipshoe.domain.wish.dto.WishResponseDto;
import com.hanghae.shipshoe.domain.wish.entity.Wish;
import com.hanghae.shipshoe.domain.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public WishResponseDto wish(WishRequestDto request) {
        User findUser = userRepository.findById(request.getUserId()).orElseThrow();
        Item findItem = itemRepository.findById(request.getItemId()).orElseThrow();

        Wish wish = Wish.createWish(findUser, findItem);
        wishRepository.save(wish);

        return new WishResponseDto(wish);
    }

    public List<WishResponseDto> getWish(Long userId) {
        List<Wish> wishes = wishRepository.findByUserId(userId);
        return wishes.stream()
                .map(WishResponseDto::new)
                .toList();
    }

    @Transactional
    public void delete(Long wishId) {
        Wish findWish = wishRepository.findById(wishId).orElseThrow();
        wishRepository.delete(findWish);
    }
}
