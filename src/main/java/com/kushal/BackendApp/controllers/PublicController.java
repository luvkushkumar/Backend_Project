package com.kushal.BackendApp.controllers;

import com.kushal.BackendApp.AppCache.AppCache;
import com.kushal.BackendApp.Entities.Users;
import com.kushal.BackendApp.repo.UserRepo;
import com.kushal.BackendApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @PostMapping("/create-user")
    public void createUser(@RequestBody Users user)
    {
            userService.createUser(user);
    }


    @GetMapping("/clear-cache")
    public void init()
    {
        appCache.init();
    }

}
