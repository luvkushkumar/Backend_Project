package com.kushal.BackendApp.controllers;

import com.kushal.BackendApp.Entities.Users;
import com.kushal.BackendApp.repo.UserRepo;
import com.kushal.BackendApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody Users user)
    {
            userService.createUser(user);
    }

}
