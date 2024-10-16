package com.example.librarymanagement.controller;


import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }


    public void setBookService(LibraryService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/borrow/{userId}/{bookId}")
    public String borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        boolean success = bookService.borrowBook(userId, bookId);
        return success ? "Book borrowed successfully!" : "Unable to borrow book.";
    }

    @PostMapping("/return/{userId}/{bookId}")
    public String returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        boolean success = bookService.returnBook(userId, bookId);
        return success ? "Book returned successfully!" : "Unable to return book.";
    }

}
