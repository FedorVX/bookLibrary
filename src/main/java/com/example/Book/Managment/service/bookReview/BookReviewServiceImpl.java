package com.example.Book.Managment.service.bookReview;

import com.example.Book.Managment.entity.Book;
import com.example.Book.Managment.entity.BookReview;
import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.repository.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookReviewServiceImpl implements BookReviewService{
    @Autowired
    BookReviewRepository bookReviewRepository;
    @Override
    public List<BookReview> getAllReviewsByBook(Book book) {
        return bookReviewRepository.findByBook(book);
    }

    @Override
    public void save(BookReview review) {
        bookReviewRepository.save(review);
    }

    @Override
    public Optional<BookReview> findById(int id) {
        return bookReviewRepository.findById(id);
    }

    @Override
    public void delete(BookReview bookReview) {
        bookReviewRepository.delete(bookReview);
    }

    @Override
    public List<BookReview> getAllReviewsByUser(User user) {
        return bookReviewRepository.findBookReviewBySender(user);
    }
}
