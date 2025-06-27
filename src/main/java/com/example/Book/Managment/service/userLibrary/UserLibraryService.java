package com.example.Book.Managment.service.userLibrary;

import com.example.Book.Managment.entity.Book;

import java.util.List;

public interface UserLibraryService {


    void takeBook(int bookId);


    void returnBook(int BookId);


    List<Book> getAllUserBooks();
}
