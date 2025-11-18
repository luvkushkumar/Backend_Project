package com.kushal.BackendApp.repo;

import com.kushal.BackendApp.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<Users,String> {

    Users findByUsername(String username);

    void deleteByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$' AND sentiment_analysis = true", nativeQuery = true)
    List<Users> getUserForSA();

}
