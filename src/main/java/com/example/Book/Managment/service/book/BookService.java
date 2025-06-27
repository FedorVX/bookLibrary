package com.example.Book.Managment.service.book;

import com.example.Book.Managment.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void save(Book book);


    List<Book> findAllBooks();


    void deleteById(int id);


    Optional<Book> findById(int id);



}
