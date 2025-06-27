package com.example.Book.Managment.repository;

import com.example.Book.Managment.entity.Book;
import com.example.Book.Managment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByBorrower(User borrower);
}
