package com.example.Book.Managment.controller;


import com.example.Book.Managment.dto.BookDto;
import com.example.Book.Managment.entity.Book;
import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.enums.BookStatus;
import com.example.Book.Managment.exception.BookNotFoundException;
import com.example.Book.Managment.service.CurrentUserService;
import com.example.Book.Managment.service.book.BookService;
import com.example.Book.Managment.service.userLibrary.UserLibraryService;
import org.apache.catalina.util.ResourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;


    @Autowired
    private UserLibraryService userLibraryService;


    @Autowired
    private CurrentUserService currentUserService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }





    @GetMapping("/books/new")
    public String addBookPage(Model model){
        model.addAttribute("book", new BookDto());
        return "add-book";
    }



    @PostMapping("/books/new")
    public String saveAddBook(@ModelAttribute("book") BookDto bookDto,
                             @RequestParam("file") MultipartFile file) throws IOException {
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setName(bookDto.getName());
        book.setPrice(bookDto.getPrice());
        book.setStatus(BookStatus.AVAILABLE);
        String uploadDir = "uploads/books/";
        Files.createDirectories(Paths.get(uploadDir));
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        book.setFilePath(filename);
        bookService.save(book);
        return "redirect:/books";
    }


    @GetMapping("/books")
    public String showAllBooks(Model model){
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("BORROWED", BookStatus.BORROWED);
        return "books";
    }


    @PostMapping("/books/take/{id}")
    public String takeBook(@PathVariable int id) throws BookNotFoundException {
        userLibraryService.takeBook(id);

        return "redirect:/books";
    }


    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable int id){
        bookService.deleteById(id);
        return "redirect:/books";
    }


    @GetMapping("/my-books")
    public String userLibraryPage(Model model){
        List<Book> userBooks = userLibraryService.getAllUserBooks();
        model.addAttribute("user_books", userBooks);
        return "user-library-page";
    }


    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBookNotFound() {
        return "error/404";  // Путь к твоему шаблону ошибки (например, resources/templates/error/404.html)
    }


    @PostMapping("/my-books/return-book/{id}")
    public String returnBook(@PathVariable("id") int id){
        Book returnedBook = bookService.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book can not be return"));
        User borrower = returnedBook.getBorrower();
        borrower.getUserBooks().remove(returnedBook);
        returnedBook.setStatus(BookStatus.AVAILABLE);
        returnedBook.setBorrower(null);
        bookService.save(returnedBook);
        return "redirect:/my-books";
    }


    @GetMapping("books/download/{id}")
    public ResponseEntity<Resource> downloadBookFile(@PathVariable int id) throws IOException{
        Book book = bookService.findById(id).orElseThrow(()->new BookNotFoundException("Book not found"));
        Path file = Paths.get("uploads/books").resolve(book.getFilePath());
        Resource resource = new UrlResource(file.toUri());
        if(!resource.exists() || !resource.isReadable()){
            throw new RuntimeException("Could not read file:" + book.getFilePath());
        }
        String encodedFilename = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8)
                .replace("+", "%20");


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''"+encodedFilename)
                .body(resource);
    }
}
