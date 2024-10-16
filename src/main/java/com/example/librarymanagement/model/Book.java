package com.example.librarymanagement.model;



import jakarta.persistence.*;


@Entity
@Table(name = "libbook")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;



    private int copiesAvailable; // Number of copies available

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book(String title, int copiesAvailable) {
        this.title = title;
        this.copiesAvailable = copiesAvailable;
    }

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isAvailable() {
        return copiesAvailable > 0;
    }

    public void borrow() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void returnCopy() {
        copiesAvailable++;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }


}
