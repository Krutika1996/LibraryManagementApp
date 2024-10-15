package com.example.librarymanagement;

import com.example.librarymanagement.controller.BookController;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookControllerTest {
    private BookController bookController;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = mock(BookService.class);
        bookController = new BookController();
        bookController.setBookService(bookService);  // Add a setter in the controller for service injection
    }

    @Test
    void testGetAllBooks_EmptyLibrary() {
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

        List<Book> books = bookController.getAllBooks();

        assertTrue(books.isEmpty(), "Library should be empty");
    }

    @Test
    void testGetAllBooks_NonEmptyLibrary() {
        Book book1 = new Book("Book 1", null);
        Book book2 = new Book("Book 2", null);
        when(bookService.getAllBooks()).thenReturn(List.of(book1, book2));

        List<Book> books = bookController.getAllBooks();

        assertEquals(2, books.size(), "Library should contain two books");
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }

}
