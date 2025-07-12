package com.example.Book.Managment.controller;


import com.example.Book.Managment.entity.BookReview;
import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.exception.UserNotFoundException;
import com.example.Book.Managment.service.CurrentUserService;
import com.example.Book.Managment.service.bookReview.BookReviewService;
import com.example.Book.Managment.service.user.UserService;
import jdk.jfr.Registered;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("profile")
public class ProfileController {
    private final CurrentUserService currentUserService;
    private final UserService userService;
    private final BookReviewService bookReviewService;


    public ProfileController(CurrentUserService currentUserService, UserService userService, BookReviewService bookReviewService){
        this.currentUserService = currentUserService;
        this.bookReviewService = bookReviewService;
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public String showProfile(@PathVariable("id") int id, Model model){
        User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<BookReview> reviews =  bookReviewService.getAllReviewsByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        return "profile";
    }

}
