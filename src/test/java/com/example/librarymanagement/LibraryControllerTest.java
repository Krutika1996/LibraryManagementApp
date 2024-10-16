package com.example.librarymanagement;

import com.example.librarymanagement.controller.LibraryController;
import com.example.librarymanagement.exception.ResourceNotFoundException;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibraryControllerTest {
    private LibraryController libraryController;
    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        libraryService = mock(LibraryService.class);
        libraryController = new LibraryController();
        libraryController.setBookService(libraryService);  // Add a setter in the controller for service injection
    }

    @Test
    void testGetAllBooks_EmptyLibrary() {
        when(libraryService.getAllBooks()).thenReturn(Collections.emptyList());

        List<Book> books = libraryController.getAllBooks();

        assertTrue(books.isEmpty(), "Library should be empty");
    }

    @Test
    void testGetAllBooks_NonEmptyLibrary() {
        Book book1 = new Book(Long.valueOf("Book 1"), null);
        Book book2 = new Book(Long.valueOf("Book 2"), null);
        when(libraryService.getAllBooks()).thenReturn(List.of(book1, book2));

        List<Book> books = libraryController.getAllBooks();

        assertEquals(2, books.size(), "Library should contain two books");
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }
    @Test
    void testBorrowBook_WhenSuccess() {
        // Given
        Long userId = 1L;
        Long bookId = 1L;
        when(libraryService.borrowBook(userId, bookId)).thenReturn(true);

        // When
        String response = libraryController.borrowBook(userId, bookId);

        // Then
        assertEquals("Book borrowed successfully!", response);
        verify(libraryService, times(1)).borrowBook(userId, bookId);
    }

    @Test
    void testBorrowBook_WhenFailure() {
        // Given
        Long userId = 1L;
        Long bookId = 1L;
        when(libraryService.borrowBook(userId, bookId)).thenReturn(false);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            libraryController.borrowBook(userId, bookId);
        });
        assertEquals("Unable to borrow book with ID 1 for user with ID 1", exception.getMessage());
        verify(libraryService, times(1)).borrowBook(userId, bookId);
    }
    @Test
    void testReturnBook_WhenSuccess() {
        // Given
        Long userId = 1L;
        Long bookId = 1L;
        when(libraryService.returnBook(userId, bookId)).thenReturn(true);
        String response = libraryController.returnBook(userId, bookId);
        assertEquals("Book returned successfully!", response);
        verify(libraryService, times(1)).returnBook(userId, bookId);
    }
    @Test
    void testReturnBook_WhenFailure() {
        // Given
        Long userId = 1L;
        Long bookId = 1L;
        when(libraryService.returnBook(userId, bookId)).thenReturn(false);
        String response = libraryController.returnBook(userId, bookId);
        assertEquals("Unable to return book.", response);
        verify(libraryService, times(1)).returnBook(userId, bookId);
    }
}
