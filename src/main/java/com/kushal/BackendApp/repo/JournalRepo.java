package com.kushal.BackendApp.repo;

import com.kushal.BackendApp.Entities.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepo extends JpaRepository<JournalEntry,String> {
}
