package com.example.librarymanagement.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToMany
    private List<Book> borrowedBooks = new ArrayList<>();

    public boolean canBorrow() {
        return borrowedBooks.size() < 2; // Limit of 2 books
    }

    public void borrowBook(Book book) {
        if (canBorrow()) {
            borrowedBooks.add(book);
        }
    }

}
