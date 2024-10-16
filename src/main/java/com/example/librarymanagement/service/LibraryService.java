package com.example.librarymanagement.service;


import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
    public boolean borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user != null && book != null && book.isAvailable() && user.canBorrow()) {
            user.borrowBook(book);
            book.borrow(); // Decrease the number of copies available
            userRepository.save(user);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    public boolean returnBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user != null && book != null && user.getBorrowedBooks().contains(book)) {
            user.returnBook(book);
            book.returnCopy(); // Increase the number of copies available
            userRepository.save(user);
            bookRepository.save(book);
            return true;
        }
        return false;
    }
}
