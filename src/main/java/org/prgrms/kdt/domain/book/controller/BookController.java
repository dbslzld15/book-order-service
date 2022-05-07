package org.prgrms.kdt.domain.book.controller;

import org.prgrms.kdt.domain.book.entity.Book;
import org.prgrms.kdt.domain.book.request.BookCreateRequest;
import org.prgrms.kdt.domain.book.request.BookUpdateRequest;
import org.prgrms.kdt.domain.book.service.BookService;
import org.prgrms.kdt.domain.user.entity.User;
import org.prgrms.kdt.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllBooks(Model model, HttpSession session) {
        long userId = Long.parseLong(String.valueOf(session.getAttribute("userId")));
        User user = userService.getUserById(userId);
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("address", user.getAddress().getAddress());
        return "books/list";
    }

    @GetMapping("/admin")
    public String getAllAdminBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/admin_list";
    }

    @GetMapping("/new")
    public String createBookPage() {
        return "books/new";
    }

    @PostMapping("/new")
    public String createBook(@Valid BookCreateRequest createRequest) {
        bookService.save(createRequest);
        return "books/admin_list";
    }

    @GetMapping("/{bookId}")
    public String getBook(Model model, @PathVariable long bookId) {
        Book book = bookService.getBook(bookId);
        model.addAttribute("book", book);
        return "books/detail";
    }

    @PutMapping("/{bookId}")
    public String modifyBook(
            @PathVariable long bookId, @Valid BookUpdateRequest updateRequest) {
        bookService.update(bookId, updateRequest);
        return "books/admin_list";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable long bookId) {
        bookService.remove(bookId);
        return "books/admin_list";
    }

}
