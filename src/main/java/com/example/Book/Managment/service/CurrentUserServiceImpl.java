package com.example.Book.Managment.service;

import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.repository.UserRepository;
import com.example.Book.Managment.service.userLibrary.UserLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CurrentUserServiceImpl implements CurrentUserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public User getCurrentUser() {
        String username = getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }


    private String getCurrentUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
