package org.prgrms.kdt.domain.book.repository;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.vo.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JdbcItemRepositoryTest {

    @Autowired
    JdbcItemRepository itemRepository;

    @Test
    void save() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Item item = new Item(1L, new Price(1000L), 30);
        //when
        long savedId = itemRepository.insert(item);
        Optional<Item> findItem = itemRepository.findById(savedId);
        //then
        assertThat(findItem.get().getPrice()).isEqualTo(item.getPrice());
    }

    @Test
    void update() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Item item = new Item(1L, new Price(1000L), 30);
        long savedId = itemRepository.insert(item);
        Item updateItem = new Item(1L, new Price(9000L), 20);
        //when
        long updatedRows = itemRepository.update(updateItem);
        //then
        assertThat(updatedRows).isEqualTo(1);
    }

    @Test
    void deleteById() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Item item = new Item(1L, new Price(1000L), 30);
        long savedId = itemRepository.insert(item);
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
        Item item = new Item(1L, new Price(1000L), 30);
        long savedId = itemRepository.insert(item);
        //when
        itemRepository.deleteAll();
        Optional<Item> findItem = itemRepository.findById(savedId);
        //then
        assertThat(findItem).isEmpty();
    }
}