package com.kushal.BackendApp.controllers;

import com.kushal.BackendApp.AppCache.AppCache;
import com.kushal.BackendApp.Entities.Users;
import com.kushal.BackendApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<List<Users>> getAllUsers()
    {
        List<Users> allUsers = userService.findAllUsers();
        if(allUsers != null && !allUsers.isEmpty() )
        {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public void saveAdmin(@RequestBody Users users)
    {
        userService.saveAdmin(users);
    }



    @GetMapping("/clear-cache")
    public void init()
    {
        appCache.init();
    }

}
