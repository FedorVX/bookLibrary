package com.example.Book.Managment.service.userLibrary;

import com.example.Book.Managment.entity.Book;
import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.enums.BookStatus;
import com.example.Book.Managment.exception.BookNotFoundException;
import com.example.Book.Managment.repository.BookRepository;
import com.example.Book.Managment.repository.UserRepository;
import com.example.Book.Managment.service.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserLibraryServiceImpl implements UserLibraryService{


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public void takeBook(int bookId) {
       Book book = bookRepository.findById(bookId)
               .orElseThrow(() -> new BookNotFoundException("Book not found"));
       User borrower = currentUserService.getCurrentUser();
       book.setBorrower(borrower);
       book.setStatus(BookStatus.BORROWED);
       bookRepository.save(book);
    }

    @Override
    public void returnBook(int BookId) {

    }

    @Override
    public List<Book> getAllUserBooks() {
        User borrower = currentUserService.getCurrentUser();
        return bookRepository.findByBorrower(borrower);
    }
}
