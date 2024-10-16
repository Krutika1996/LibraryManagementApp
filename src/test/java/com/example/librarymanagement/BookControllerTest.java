package com.example.librarymanagement;

import com.example.librarymanagement.controller.LibraryController;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.service.LibraryService;
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
    private LibraryController bookController;
    private LibraryService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = mock(LibraryService.class);
        bookController = new LibraryController();
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
        Book book1 = new Book(Long.valueOf("Book 1"), null);
        Book book2 = new Book(Long.valueOf("Book 2"), null);
        when(bookService.getAllBooks()).thenReturn(List.of(book1, book2));

        List<Book> books = bookController.getAllBooks();

        assertEquals(2, books.size(), "Library should contain two books");
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }

}
