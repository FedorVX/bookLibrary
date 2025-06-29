package com.example.Book.Managment.dto;

public class BookDto {
    private String name;


    private String author;


    private String price;


    private String filePath;


    private String coverPath;


    public BookDto(String name, String author, String price, String filePath, String coverPath) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.filePath = filePath;
        this.coverPath = coverPath;
    }


    public BookDto() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
