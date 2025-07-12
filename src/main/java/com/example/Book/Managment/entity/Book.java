package com.example.Book.Managment.entity;


import com.example.Book.Managment.enums.BookStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;

    private String filePath;


    private String coverPath;


    private String author;
    private String price;
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User borrower;

    @OneToMany(mappedBy = "book")
    private List<BookReview> reviews;


    public Book(String name, String filePath, String coverPath, String author, String price, BookStatus status, User borrower) {
        this.name = name;
        this.filePath = filePath;
        this.coverPath = coverPath;
        this.author = author;
        this.price = price;
        this.status = status;
        this.borrower = borrower;
    }


    public Book() {
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }
}
