package com.example.Book.Managment.service.role;

import com.example.Book.Managment.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);


    void saveRole(Role role);


    List<Role> findAll();
}
