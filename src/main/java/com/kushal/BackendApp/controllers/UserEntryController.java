package com.kushal.BackendApp.controllers;

import com.kushal.BackendApp.Entities.Users;
import com.kushal.BackendApp.apiResponse.WeatherResponse;
import com.kushal.BackendApp.service.UserService;
import com.kushal.BackendApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUserByName(@RequestBody Users users)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users usersInDb =  userService.getUserByName(username);

        usersInDb.setUsername(users.getUsername());
        usersInDb.setPassword(users.getPassword());
        userService.createUser(usersInDb);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        userService.deleteUserByUserName(userName);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greetings = "";
        if(weatherResponse != null)
        {
            greetings =  "Weather feels like "+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+userName+" "+greetings,HttpStatus.OK);
    }
}
