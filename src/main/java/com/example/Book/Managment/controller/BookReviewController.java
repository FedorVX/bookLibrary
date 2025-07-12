package com.example.Book.Managment.controller;

import com.example.Book.Managment.entity.Book;
import com.example.Book.Managment.entity.BookReview;
import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.enums.Role;
import com.example.Book.Managment.exception.BookNotFoundException;
import com.example.Book.Managment.exception.BookReviewNotFoundException;
import com.example.Book.Managment.service.CurrentUserService;
import com.example.Book.Managment.service.book.BookService;
import com.example.Book.Managment.service.bookReview.BookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookReviewController {
    @Autowired
    BookService bookService;
    @Autowired
    BookReviewService bookReviewService;

    @Autowired
    CurrentUserService currentUserService;
    @GetMapping("/review/{id}")
    public String getBookReviews(@PathVariable("id") int id,
                                 Model model){
        Book book = bookService.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        List<BookReview> reviews = bookReviewService.getAllReviewsByBook(book);
        User currentUser = currentUserService.getCurrentUser();
        model.addAttribute("current_user", currentUser);
        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getName().equals(Role.ROLE_ADMIN.name()));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("reviews", reviews);
        return "book-review";
    }

    @GetMapping("/review/add/{id}")
    public String addBookPage(@PathVariable("id") int id,
                              Model model){
        Book book = bookService.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        model.addAttribute("book", book);
        model.addAttribute("book_review", new BookReview());
        return "add-book-review";
    }

    @PostMapping("/review/add")
    public String addReview(@ModelAttribute("book_review") BookReview review,
                            @RequestParam("book_id") int book_id){
        Book book = bookService.findById(book_id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        review.setBook(book);
        User currentUser = currentUserService.getCurrentUser();
        review.setSender(currentUser);
        bookReviewService.save(review);
        return "redirect:/my-books";
    }


    @PostMapping("review/delete/{id}")
    public String deleteReview(@PathVariable("id") int id){
        BookReview bookReview = bookReviewService.findById(id).orElseThrow(()-> new BookReviewNotFoundException("Book review not found"));
        bookReviewService.delete(bookReview);
        return "redirect:/book-review";
    }
}
