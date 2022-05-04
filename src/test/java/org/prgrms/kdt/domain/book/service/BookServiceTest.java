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
        Book firstBook = Book.builder()
                .title(new Title("오브젝트"))
                .authorName(new Name("조영호"))
                .price(new Price(10000L))
                .stockQuantity(20)
                .itemId(1L)
                .createdDateTime(now())
                .modifiedDateTime(now()).build();
        Book secondBook = Book.builder()
                .title(new Title("객체지향의 사실과 오해"))
                .authorName(new Name("조영호"))
                .price(new Price(20000L))
                .stockQuantity(30)
                .itemId(2L)
                .createdDateTime(now())
                .modifiedDateTime(now()).build();
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
        Book book = Book.builder()
                .bookId(bookId)
                .title(new Title("오브젝트"))
                .authorName(new Name("조영호"))
                .price(new Price(10000L))
                .stockQuantity(20)
                .itemId(1L)
                .createdDateTime(now())
                .modifiedDateTime(now()).build();
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
        Book book = Book.builder()
                .bookId(bookId)
                .title(new Title("객체지향"))
                .authorName(new Name("조영호"))
                .price(new Price(10000L))
                .stockQuantity(20)
                .itemId(1L)
                .createdDateTime(now())
                .modifiedDateTime(now()).build();
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
        Book book = Book.builder()
                .bookId(bookId)
                .title(new Title("객체지향"))
                .authorName(new Name("조영호"))
                .price(new Price(10000L))
                .stockQuantity(20)
                .itemId(1L)
                .createdDateTime(now())
                .modifiedDateTime(now()).build();
        //when
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        bookService.remove(bookId);
        //then
        verify(bookRepository, times(1)).findById(anyLong());
        verify(itemService, times(1)).remove(anyLong());
        verify(bookRepository, times(1)).deleteById(anyLong());
    }
}