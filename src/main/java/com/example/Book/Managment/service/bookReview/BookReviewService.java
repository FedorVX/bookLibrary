package com.example.Book.Managment.service.bookReview;


import com.example.Book.Managment.entity.Book;
import com.example.Book.Managment.entity.BookReview;
import com.example.Book.Managment.entity.User;

import java.util.List;
import java.util.Optional;

public interface BookReviewService {
    List<BookReview> getAllReviewsByBook(Book book);

    void save(BookReview review);

   Optional<BookReview> findById(int id);

    void delete(BookReview bookReview);

    List<BookReview> getAllReviewsByUser(User user);
}
