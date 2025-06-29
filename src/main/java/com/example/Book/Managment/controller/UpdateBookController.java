package com.example.Book.Managment.controller;


import com.example.Book.Managment.dto.BookDto;
import com.example.Book.Managment.entity.Book;
import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.enums.Role;
import com.example.Book.Managment.exception.BookNotFoundException;
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
public class UpdateBookController {
    @Autowired
    BookService bookService;
    @Autowired
    CurrentUserService currentUserService;
    @RequestMapping("/books/update/{id}")


    @GetMapping
    public String updateBook(@PathVariable("id") int id, Model model){
        Book book = bookService.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        model.addAttribute("book", book);
        User currentUser = currentUserService.getCurrentUser();
        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getName().equals(Role.ROLE_ADMIN.name()));
        model.addAttribute("isAdmin", isAdmin);
        return "update-book";
    }


    @PostMapping
    public String updateBook(@PathVariable("id") int id,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("coverFile") MultipartFile coverFile,
                             @ModelAttribute("book") BookDto bookDto)
    throws IOException {
        Book book = bookService.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());
        book.setName(bookDto.getName());


        if(!file.isEmpty()){
            String uploadDir = "uploads/books";
            Files.createDirectories(Paths.get(uploadDir));
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            book.setFilePath(filename);
        }

        if(!coverFile.isEmpty()){
            String coverUploadDir = "uploads/covers";
            Files.createDirectories(Paths.get(coverUploadDir));
            String coverFilename = UUID.randomUUID() + "_" + coverFile.getOriginalFilename();
            Path coverFilePath = Paths.get(coverUploadDir, coverFilename);
            Files.copy(coverFile.getInputStream(), coverFilePath, StandardCopyOption.REPLACE_EXISTING);
            book.setCoverPath(coverFilename);
        }

        bookService.save(book);
        return "redirect:/books";

    }
}
