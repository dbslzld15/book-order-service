package org.prgrms.kdt.domain.book.controller;

import org.prgrms.kdt.domain.book.entity.Book;
import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.request.BookCreateRequest;
import org.prgrms.kdt.domain.book.request.BookUpdateRequest;
import org.prgrms.kdt.domain.book.service.BookService;
import org.prgrms.kdt.domain.book.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ItemService itemService;

    public BookController(BookService bookService, ItemService itemService) {
        this.bookService = bookService;
        this.itemService = itemService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/list";
    }

    @PostMapping
    public String createBook(@Valid BookCreateRequest createRequest) {
        bookService.save(createRequest);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}")
    public String getBook(Model model, @PathVariable long bookId) {
        Book book = bookService.getBook(bookId);
        Item item = itemService.getByItemId(book.getItemId());
        model.addAttribute("book", book);
        model.addAttribute("item", item);
        return "books/detail";
    }

    @PutMapping("/{bookId}")
    public String modifyBook(
            @PathVariable long bookId, @Valid BookUpdateRequest updateRequest) {
        bookService.update(bookId, updateRequest);
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable long bookId) {
        bookService.remove(bookId);
        return "redirect:/books";
    }

}
