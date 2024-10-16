package com.example.librarymanagement;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.UserRepository;
import com.example.librarymanagement.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class LibraryServiceTest {
    @InjectMocks
    private LibraryService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    public LibraryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    private User user;
    private Book book;

    @Test
    void testGetAllBooks() {
        Book book1 = new Book(Long.valueOf("Book 1"), "11 seconds");
        Book book2 = new Book(Long.valueOf("Book 2"), "kite runner");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    void testAddBook() {
        Book book = new Book(Long.valueOf("Book 3"), "New Book");
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(book);
        assertNotNull(savedBook);
        assertEquals("New Book", savedBook.getTitle());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("Test User");
        book = new Book();
        book.setTitle("Test Book");
    }

    @Test
    void testBorrowBook_Success_MultipleCopies() {
        book.setCopiesAvailable(3); // More than one copy available
        user.setBorrowedBooks(new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.borrowBook(1L, 1L);
        assertTrue(result);
        assertEquals(2, book.getCopiesAvailable()); // One copy borrowed, two left
        assertEquals(1, user.getBorrowedBooks().size());
    }

    @Test
    void testBorrowBook_Success_SingleCopy() {
        book.setCopiesAvailable(1); // Only one copy available
        user.setBorrowedBooks(new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.borrowBook(1L, 1L);
        assertTrue(result);
        assertEquals(0, book.getCopiesAvailable()); // Book removed from library
        assertEquals(1, user.getBorrowedBooks().size());
    }

    @Test
    void testBorrowBook_Failure_UserLimit() {
        book.setCopiesAvailable(3); // More than one copy available
        user.setBorrowedBooks(Arrays.asList(new Book(), new Book())); // Limit reached
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.borrowBook(1L, 1L);
        assertFalse(result);
        assertEquals(3, book.getCopiesAvailable()); // Copies remain unchanged
    }

    @Test
    void testBorrowBook_Failure_NoCopiesAvailable() {
        book.setCopiesAvailable(0); // No copies available
        user.setBorrowedBooks(new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.borrowBook(1L, 1L);
        assertFalse(result);
        assertEquals(0, book.getCopiesAvailable()); // No change in copies
    }
    @Test
    void testReturnBook_Success() {
        book.setCopiesAvailable(1); // Initially 1 copy available
        user.setBorrowedBooks(new ArrayList<>(Arrays.asList(book)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.returnBook(1L, 1L);
        assertTrue(result);
        assertEquals(2, book.getCopiesAvailable()); // One copy returned, two available now
        assertEquals(0, user.getBorrowedBooks().size()); // Borrowed list should be empty
    }

    @Test
    void testReturnBook_Failure_NotBorrowed() {
        Book anotherBook = new Book();
        anotherBook.setTitle("Another Book");
        user.setBorrowedBooks(new ArrayList<>(Arrays.asList(anotherBook))); // User has a different book
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.returnBook(1L, 1L);
        assertFalse(result);
        assertEquals(1, book.getCopiesAvailable()); // No change in copies
        assertEquals(1, user.getBorrowedBooks().size()); // User still has the other book
    }
}
