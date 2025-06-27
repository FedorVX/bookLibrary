package com.example.Book.Managment.config;

import com.example.Book.Managment.entity.Role;
import com.example.Book.Managment.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    RoleRepository roleRepository;


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
    }
}
