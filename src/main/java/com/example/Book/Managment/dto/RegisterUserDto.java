package com.example.Book.Managment.dto;

public class RegisterUserDto {
    private String name;
    private String username;
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public RegisterUserDto(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }


    public RegisterUserDto() {
    }
}
