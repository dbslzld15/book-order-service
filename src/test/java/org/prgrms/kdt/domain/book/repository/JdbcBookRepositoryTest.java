package org.prgrms.kdt.domain.book.repository;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.book.entity.Book;
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

    @Test
    void save() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
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
        LocalDateTime now = LocalDateTime.now();
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        Book secondBook = Book.builder()
                .title(new Title("오브젝트"))
                .authorName(new Name("조영호"))
                .itemId(2L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        bookRepository.save(book);
        bookRepository.save(secondBook);
        //when
        List<Book> books = bookRepository.findAll();
        //then
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void findById() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        long savedId = bookRepository.save(book);
        //when
        Optional<Book> findBook = bookRepository.findById(savedId);
        //then
        assertThat(findBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(findBook.get().getAuthorName()).isEqualTo(book.getAuthorName());
    }

    @Test
    void update() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        long savedId = bookRepository.save(book);
        Book updateBook = Book.builder()
                .bookId(savedId)
                .title(new Title("오브젝트"))
                .authorName(new Name("조영호"))
                .itemId(2L)
                .createdDateTime(now)
                .modifiedDateTime(now)
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
                .createdDateTime(now)
                .modifiedDateTime(now)
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
        LocalDateTime now = LocalDateTime.now();
        Book book = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .itemId(1L)
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        long savedId = bookRepository.save(book);
        //when
        bookRepository.deleteAll();
        //then
        Optional<Book> findBook = bookRepository.findById(savedId);
        assertThat(findBook).isEmpty();
    }
}