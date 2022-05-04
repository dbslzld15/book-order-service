package org.prgrms.kdt.domain.book.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.book.entity.Book;
import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.repository.BookRepository;
import org.prgrms.kdt.domain.book.request.BookCreateRequest;
import org.prgrms.kdt.domain.book.request.BookUpdateRequest;
import org.prgrms.kdt.domain.book.vo.Price;
import org.prgrms.kdt.domain.book.vo.Title;
import org.prgrms.kdt.domain.user.vo.Name;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    ItemService itemService;

    @InjectMocks
    BookService bookService;

    @Test
    void save() {
        //given
        Long bookId = 1L;
        BookCreateRequest request = new BookCreateRequest(
                "객체지향", "조영호", 10000L, 10);
        //when
        when(bookRepository.save(any())).thenReturn(bookId);
        long savedId = bookService.save(request);
        //then
        assertThat(savedId).isEqualTo(bookId);
    }

    @Test
    void getAllBooks() {
        //given
        Book firstBook = new Book(now(), now(), 1L, new Title("객체지향"), new Name("조영호"), 1L);
        Book secondBook = new Book(now(), now(), 2L, new Title("오브젝트"), new Name("조영호"), 2L);
        List<Book> books = List.of(firstBook, secondBook);
        //when
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> findBooks = bookService.getAllBooks();
        //then
        assertThat(findBooks).contains(firstBook, secondBook);
    }

    @Test
    void getBook() {
        //given
        long bookId = 1L;
        Book book = new Book(now(), now(), bookId, new Title("객체지향"), new Name("조영호"), 1L);
        //when
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        Book findBook = bookService.getBook(bookId);
        //then
        assertThat(findBook).isEqualTo(book);
    }

    @Test
    void update() {
        //given
        long bookId = 1L;
        long price = 10000L;
        int stockQuantity = 20;
        BookUpdateRequest request = new BookUpdateRequest("객체지향", "조영호", price, stockQuantity);
        Book book = new Book(now(), now(), bookId, new Title("객체지향"), new Name("조영호"), 1L);
        Item item = new Item(now(), now(), 1L, new Price(price), stockQuantity);
        //when
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(itemService.getItemById(anyLong())).thenReturn(item);
        bookService.update(bookId, request);
        //then
        verify(bookRepository, times(1)).findById(anyLong());
        verify(itemService, times(1)).getItemById(anyLong());
        verify(itemService, times(1)).update(anyLong(), anyLong(), anyInt());
        verify(bookRepository, times(1)).update(any());
    }

    @Test
    void remove() {
        //given
        long bookId = 1L;
        Book book = new Book(now(), now(), bookId, new Title("객체지향"), new Name("조영호"), 1L);
        //when
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        bookService.remove(bookId);
        //then
        verify(bookRepository, times(1)).findById(anyLong());
        verify(itemService, times(1)).remove(anyLong());
        verify(bookRepository, times(1)).deleteById(anyLong());
    }
}