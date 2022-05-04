package org.prgrms.kdt.domain.book.repository;

import org.prgrms.kdt.domain.book.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(long bookId);

    long insert(Book book);

    int update(Book book);

    void deleteById(long bookId);

    void deleteAll();
}
