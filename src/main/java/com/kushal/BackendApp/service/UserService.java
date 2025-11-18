package com.kushal.BackendApp.service;

import com.kushal.BackendApp.Entities.Users;
import com.kushal.BackendApp.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

        @Autowired
        private static final  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void createUser(Users user) {
        try
        {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("ADMIN"));
            userRepo.save(user);
        }
        catch (Exception e)
        {
                logger.info("some error.......to save");
        }


    }

    public List<Users> findAllUsers() {
        return userRepo.findAll();
    }

    public void saveAdmin(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(users);
    }

    public Users getUserByName(String username) {
        return userRepo.findByUsername(username);
    }

    public void deleteUserByUserName(String username) {

        userRepo.deleteByUsername(username);

    }

    public void saveExistingUser(Users user) {

        userRepo.save(user);
    }
}
