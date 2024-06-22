package com.hanghae.shipshoe.domain.item.service;

import com.hanghae.shipshoe.domain.item.dto.ItemRequestDto;
import com.hanghae.shipshoe.domain.item.dto.ItemResponseDto;
import com.hanghae.shipshoe.domain.item.entity.Item;
import com.hanghae.shipshoe.domain.item.repository.ItemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponseDto> findItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(ItemResponseDto::new)
                .toList();
    }

    public ItemResponseDto findOne(Long id) {
        Item findItem = itemRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new ItemResponseDto(findItem);
    }

    @Transactional
    public ItemResponseDto create(ItemRequestDto request) {
        Item item = new Item();
        item.createItem(request);
        Long id = itemRepository.save(item).getId();
        Item findItem = itemRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new ItemResponseDto(findItem);
    }
}
