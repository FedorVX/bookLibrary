package com.example.Book.Managment.config;

import com.example.Book.Managment.entity.Role;
import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.repository.RoleRepository;
import com.example.Book.Managment.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class DataInitializer {

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init(){
        if(roleRepository.count() == 0){
            Role userRole = new Role();
            userRole.setName(com.example.Book.Managment.enums.Role.ROLE_USER.name());
            roleRepository.save(userRole);


            Role adminRole = new Role();
            adminRole.setName(com.example.Book.Managment.enums.Role.ROLE_ADMIN.name());
            roleRepository.save(adminRole);
            System.out.println("Roles were initialized");
        }
        Optional<Role> optionalRole = roleRepository.findByName(com.example.Book.Managment.enums.Role.ROLE_ADMIN.name());
        optionalRole.ifPresent(role -> {
            if(!userRepository.existsByUsername("admin")) {
                User user = new User();
                user.setRoles(Collections.singleton(role));
                user.setUsername("admin");
                user.setName("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                userRepository.save(user);
            }
            });
        }
    }
