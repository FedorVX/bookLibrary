package com.example.Book.Managment.controller;


import com.example.Book.Managment.dto.BookDto;
import com.example.Book.Managment.entity.Book;
import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.enums.BookStatus;
import com.example.Book.Managment.enums.Role;
import com.example.Book.Managment.service.CurrentUserService;
import com.example.Book.Managment.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
public class NewBookController {
    @Autowired
    BookService bookService;

    @Autowired
    CurrentUserService currentUserService;

    @RequestMapping("/books/new")
    @PostMapping
    public String addNewBook(@ModelAttribute("book") BookDto bookDto,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("coverFile") MultipartFile coverFile
    ) throws IOException{
        Book book = new Book();
        book.setPrice(bookDto.getPrice());
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setStatus(BookStatus.AVAILABLE);


        String uploadDir = "uploads/books";
        Files.createDirectories(Paths.get(uploadDir));
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        book.setFilePath(filename);


        String uploadCoverDir = "uploads/covers";
        Files.createDirectories(Paths.get(uploadCoverDir));
        String coverFilename = UUID.randomUUID() + "_" + coverFile.getOriginalFilename();
        Path fileCoverPath = Paths.get(uploadCoverDir, coverFilename);
        Files.copy(coverFile.getInputStream(), fileCoverPath, StandardCopyOption.REPLACE_EXISTING);
        book.setCoverPath(coverFilename);

        bookService.save(book);
        return "redirect:/books";

    }


    @GetMapping
    public String addNewBook(Model model){
        User currentUser = currentUserService.getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("book", new BookDto());
        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getName().equals(Role.ROLE_ADMIN.name()));
        model.addAttribute("isAdmin", isAdmin);
        return "add-book";
    }
}
