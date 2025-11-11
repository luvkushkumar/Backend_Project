package com.kushal.BackendApp.repo;

import com.kushal.BackendApp.Entities.ConfigContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepo extends JpaRepository<ConfigContent,String> {
}
