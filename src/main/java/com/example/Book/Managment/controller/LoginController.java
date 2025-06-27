package com.example.Book.Managment.controller;


import com.example.Book.Managment.dto.LoginUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    @ModelAttribute("user")
    public LoginUserDto loginUserDto(){
        return new LoginUserDto();
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}
