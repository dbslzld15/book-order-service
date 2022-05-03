package org.prgrms.kdt.domain.book.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.vo.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JdbcItemRepositoryTest {

    @Autowired
    JdbcItemRepository itemRepository;

    @Test
    void save() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Item item = Item.builder()
                .price(new Price(1000L))
                .stockQuantity(30)
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        //when
        long savedId = itemRepository.save(item);
        Optional<Item> findItem = itemRepository.findById(savedId);
        //then
        assertThat(findItem.get().getPrice()).isEqualTo(item.getPrice());
    }

    @Test
    void update() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Item item = Item.builder()
                .price(new Price(1000L))
                .stockQuantity(30)
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        long savedId = itemRepository.save(item);
        Item updateItem = Item.builder()
                .itemId(savedId)
                .price(new Price(9000L))
                .stockQuantity(20)
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        //when
        long updatedRows = itemRepository.update(updateItem);
        //then
        assertThat(updatedRows).isEqualTo(1);
    }

    @Test
    void deleteById() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Item item = Item.builder()
                .price(new Price(1000L))
                .stockQuantity(30)
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        long savedId = itemRepository.save(item);
        //when
        itemRepository.deleteById(savedId);
        Optional<Item> findItem = itemRepository.findById(savedId);
        //then
        assertThat(findItem).isEmpty();
    }

    @Test
    void deleteAll() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Item item = Item.builder()
                .price(new Price(1000L))
                .stockQuantity(30)
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        long savedId = itemRepository.save(item);
        //when
        itemRepository.deleteAll();
        Optional<Item> findItem = itemRepository.findById(savedId);
        //then
        assertThat(findItem).isEmpty();
    }
}