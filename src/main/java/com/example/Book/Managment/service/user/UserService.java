package com.example.Book.Managment.service.user;

import com.example.Book.Managment.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);


    void save(User user);
}
