package com.example.Book.Managment.controller;


import com.example.Book.Managment.dto.RegisterUserDto;
import com.example.Book.Managment.entity.Role;
import com.example.Book.Managment.entity.User;
import com.example.Book.Managment.service.role.RoleService;
import com.example.Book.Managment.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private RoleService roleService;


    @ModelAttribute("user")
    RegisterUserDto registerUserDto(){
        return new RegisterUserDto();
    }



    @GetMapping("/register")
    public String registerPage(){
        return "registration";
    }


    @PostMapping("/register")
    public String saveUser(@ModelAttribute("user") RegisterUserDto registerUserDto){
        User user = new User();
        user.setName(registerUserDto.getName());
        user.setUsername(registerUserDto.getUsername());
        String encodedPassword = passwordEncoder.encode(registerUserDto.getPassword());
        user.setPassword(encodedPassword);
        Role userRole = roleService.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singleton(userRole));

        userService.save(user);

        return "redirect:/register?success";
    }
}
