package org.prgrms.kdt.domain.book.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.book.entity.Book;
import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.exception.BookException;
import org.prgrms.kdt.domain.book.repository.BookRepository;
import org.prgrms.kdt.domain.book.request.BookCreateRequest;
import org.prgrms.kdt.domain.book.request.BookUpdateRequest;
import org.prgrms.kdt.domain.book.vo.Title;
import org.prgrms.kdt.domain.user.vo.Name;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.prgrms.kdt.domain.book.exception.BookExceptionType.BOOK_NOT_EXIST;

@Service
@Slf4j
@Transactional(readOnly = true)
public class BookService {
    private final ItemService itemService;
    private final BookRepository bookRepository;

    public BookService(ItemService itemService, BookRepository bookRepository) {
        this.itemService = itemService;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public long save(BookCreateRequest createRequest) {
        long itemId = itemService.save(createRequest.getPrice(), createRequest.getStockQuantity());
        Book book = Book.builder()
                .title(new Title(createRequest.getTitle()))
                .authorName(new Name(createRequest.getAuthorName()))
                .itemId(itemId)
                .build();
        return bookRepository.insert(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_EXIST));
    }

    public Book getByItemId(Long itemId) {
        return bookRepository.findByItemId(itemId)
                .orElseThrow(() -> new BookException(BOOK_NOT_EXIST));
    }

    @Transactional
    public void update(long bookId, BookUpdateRequest updateRequest) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_EXIST));
        Item item = itemService.getByItemId(book.getItemId());

        itemService.update(item.getItemId(), updateRequest.getPrice(), updateRequest.getStockQuantity());
        Book updateBook = Book.builder()
                .title(new Title(updateRequest.getTitle()))
                .authorName(new Name(updateRequest.getAuthorName()))
                .itemId(item.getItemId())
                .build();
        bookRepository.update(updateBook);
        log.info("update Book, book id: {}", book.getBookId());
    }

    @Transactional
    public void remove(long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_EXIST));
        itemService.remove(book.getItemId());
        bookRepository.deleteById(bookId);
        log.info("delete Book, book id: {}", bookId);
    }
}
