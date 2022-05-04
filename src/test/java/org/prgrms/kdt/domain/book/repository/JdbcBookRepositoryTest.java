package org.prgrms.kdt.domain.book.repository;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.book.entity.Book;
import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.vo.Price;
import org.prgrms.kdt.domain.book.vo.Title;
import org.prgrms.kdt.domain.user.vo.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JdbcBookRepositoryTest {

    @Autowired
    JdbcBookRepository bookRepository;

    @Autowired
    JdbcItemRepository itemRepository;

    @Test
    void save() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(1L)
                .build();
        //when
        long savedId = bookRepository.save(book);
        Optional<Book> findBook = bookRepository.findById(savedId);
        //then
        assertThat(findBook.get().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void findAll() {
        //given
        Item firstItem = new Item(new Price(10000L), 100);
        long firstItemId = itemRepository.save(firstItem);
        Item secondItem = new Item(new Price(5000L), 20);
        long secondItemId = itemRepository.save(secondItem);
        Book firstBook = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(firstItemId)
                .build();
        Book secondBook = Book.builder()
                .title(new Title("오브젝트"))
                .authorName(new Name("조영호"))
                .itemId(secondItemId)
                .build();
        bookRepository.save(firstBook);
        bookRepository.save(secondBook);
        //when
        List<Book> books = bookRepository.findAll();
        //then
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getPrice().getPrice())
                .isEqualTo(firstItem.getPrice().getPrice());
        assertThat(books.get(0).getStockQuantity()).isEqualTo(firstItem.getStockQuantity());
    }

    @Test
    void findById() {
        //given
        Item firstItem = new Item(new Price(10000L), 100);
        long firstItemId = itemRepository.save(firstItem);
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(firstItemId)
                .build();
        long savedId = bookRepository.save(book);
        //when
        Optional<Book> findBook = bookRepository.findById(savedId);
        //then
        assertThat(findBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(findBook.get().getAuthorName()).isEqualTo(book.getAuthorName());
        assertThat(findBook.get().getStockQuantity()).isEqualTo(firstItem.getStockQuantity());
    }

    @Test
    void update() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(1L)
                .build();
        long savedId = bookRepository.save(book);
        Book updateBook = Book.builder()
                .bookId(savedId)
                .title(new Title("오브젝트"))
                .authorName(new Name("조영호"))
                .itemId(2L)
                .build();
        //when
        int updatedRows = bookRepository.update(updateBook);
        //then
        assertThat(updatedRows).isEqualTo(1);
    }

    @Test
    void deleteById() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(1L)
                .build();
        long savedId = bookRepository.save(book);
        //when
        bookRepository.deleteById(savedId);
        //then
        Optional<Book> findBook = bookRepository.findById(savedId);
        assertThat(findBook).isEmpty();
    }

    @Test
    void deleteAll() {
        //given
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(1L)
                .build();
        long savedId = bookRepository.save(book);
        //when
        bookRepository.deleteAll();
        //then
        Optional<Book> findBook = bookRepository.findById(savedId);
        assertThat(findBook).isEmpty();
    }
}