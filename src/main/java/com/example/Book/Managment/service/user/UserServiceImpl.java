package com.example.Book.Managment.service.user;

import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
