package com.kushal.BackendApp.repo;

import com.kushal.BackendApp.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<Users,String> {

    Users findByUsername(String username);

    void deleteByUsername(String username);
}
