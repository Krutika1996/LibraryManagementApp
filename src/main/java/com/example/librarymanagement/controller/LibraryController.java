package com.example.librarymanagement.controller;


import com.example.librarymanagement.exception.BookException;
import com.example.librarymanagement.exception.ResourceNotFoundException;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.service.LibraryService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class LibraryController {

    @Autowired
    private LibraryService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) throws BadRequestException {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new BookException("Book title must not be empty");
        }
        return bookService.addBook(book);
    }


    public void setBookService(LibraryService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/borrow/{userId}/{bookId}")
    public String borrowBook(@RequestParam Long userId, @RequestParam Long bookId) throws ConfigDataResourceNotFoundException {
        boolean success = bookService.borrowBook(userId, bookId);
        if (!success) {
            throw new ResourceNotFoundException("Unable to borrow book with ID " + bookId + " for user with ID " + userId);
        }
        return "Book borrowed successfully!";
    }

    @PostMapping("/return/{userId}/{bookId}")
    public String returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        boolean success = bookService.returnBook(userId, bookId);
        return success ? "Book returned successfully!" : "Unable to return book.";
    }

}
