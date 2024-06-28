package com.hanghae.shipshoe.domain.item.api;

import com.hanghae.shipshoe.domain.item.dto.ItemRequestDto;
import com.hanghae.shipshoe.domain.item.dto.ItemResponseDto;
import com.hanghae.shipshoe.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/api/items")
    public List<ItemResponseDto> getItemList() {
        return itemService.findItems();
    }

    @GetMapping("/api/items/{id}")
    public ItemResponseDto getItem(@PathVariable("id") Long id) {
        return itemService.findOne(id);
    }

    @PostMapping("/api/items")
    public ItemResponseDto create(@RequestBody ItemRequestDto request) {
        return itemService.create(request);
    }


}
