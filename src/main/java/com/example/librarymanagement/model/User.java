package com.example.librarymanagement.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private List<String> roles = new ArrayList<>();

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {

    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToMany
    private List<Book> borrowedBooks = new ArrayList<>();
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
    public boolean canBorrow() {
        return borrowedBooks.size() < 2; // Limit of 2 books
    }

    public void borrowBook(Book book) {
        if (canBorrow()) {
            borrowedBooks.add(book);
        }
    }
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

}
