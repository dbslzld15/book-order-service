package org.prgrms.kdt.domain.book.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.repository.ItemRepository;
import org.prgrms.kdt.domain.book.vo.Price;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;

    @Test
    void save() {
        //given
        long itemId = 1L;
        //when
        when(itemRepository.save(any())).thenReturn(itemId);
        long savedId = itemService.save(10000L, 20);
        //then
        assertThat(savedId).isEqualTo(itemId);
    }

    @Test
    void getItemById() {
        //given
        long itemId = 1L;
        Item item = new Item(itemId, new Price(1000L), 30);
        //when
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        Item findItem = itemService.getItemById(itemId);
        //then
        assertThat(findItem).usingRecursiveComparison().isEqualTo(item);
    }

    @Test
    void update() {
        //given
        long itemId = 1L;
        long price = 1000L;
        int stockQuantity = 20;
        Item item = new Item(itemId, new Price(price), stockQuantity);
        //when
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        itemService.update(itemId, price, stockQuantity);
        //then
        verify(itemRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).update(any());
    }

    @Test
    void remove() {
        //given
        long itemId = 1L;
        long price = 1000L;
        int stockQuantity = 20;
        Item item = new Item(itemId, new Price(price), stockQuantity);
        //when
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        itemService.remove(itemId);
        //then
        verify(itemRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).deleteById(anyLong());
    }
}