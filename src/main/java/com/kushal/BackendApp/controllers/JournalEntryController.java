package com.kushal.BackendApp.controllers;


import com.kushal.BackendApp.Entities.JournalEntry;
import com.kushal.BackendApp.Entities.Users;
import com.kushal.BackendApp.service.JournalService;
import com.kushal.BackendApp.service.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryController.class);

    @PostMapping
    public ResponseEntity<?> saveJournalforUser(@RequestBody JournalEntry journalEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            journalService.saveJournal(username,journalEntry);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            logger.info("got an error....");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getJournalEntries()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Users user =  userService.getUserByName(userName);
        List<JournalEntry> entries = user.getJournalEntries();

        if(entries != null && !entries.isEmpty())
        {
            return  new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users user = userService.getUserByName(username);
        Optional<JournalEntry> entries = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).findFirst();

        if(entries.isPresent())
        {
            return new ResponseEntity<>(entries.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

       boolean isRemoved = journalService.deleteJournalById(username,id);
       if(isRemoved)
       {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable String id,@RequestBody JournalEntry journalEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userService.getUserByName(username);

        Optional<JournalEntry> entry =  user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).findFirst();

       if(entry.isPresent())
       {
           JournalEntry oldData = entry.get();
           oldData.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : oldData.getTitle());
           oldData.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : oldData.getContent());

           journalService.saveEntry(oldData);

           return new ResponseEntity<>(HttpStatus.OK);

       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


}
