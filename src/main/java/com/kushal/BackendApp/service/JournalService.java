package com.kushal.BackendApp.service;

import com.kushal.BackendApp.Entities.JournalEntry;
import com.kushal.BackendApp.Entities.Users;
import com.kushal.BackendApp.repo.JournalRepo;
import com.kushal.BackendApp.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalService {

    @Autowired
    private JournalRepo journalRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveJournal(String username, JournalEntry journalEntry) {

        Users user =  userRepo.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry entry = journalRepo.save(journalEntry);

        user.getJournalEntries().add(entry);
        userService.saveExistingUser(user);


    }

    public boolean deleteJournalById(String username, String id) {

        Users user = userService.getUserByName(username);
        boolean isRemoved = user.getJournalEntries().removeIf(x->x.getId().equals(id));

        if(isRemoved)
        {
            userService.saveExistingUser(user);
            return true;
        }
        return false;

    }

    public void saveEntry(JournalEntry oldData) {

        journalRepo.save(oldData);

    }
}
