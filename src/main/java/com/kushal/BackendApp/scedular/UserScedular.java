package com.kushal.BackendApp.scedular;

import com.kushal.BackendApp.Entities.JournalEntry;
import com.kushal.BackendApp.Entities.Users;
import com.kushal.BackendApp.repo.UserRepo;
import com.kushal.BackendApp.service.EmailService;
import com.kushal.BackendApp.service.SentimentAnallysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SentimentAnallysisService sentimentAnallysisService;


    @Scheduled(cron = "0 0 9 * * SUN") //seconds, minutes, hours, day of months ,month, day of week
    public void fetchUsersAndSendMail()
    {
        List<Users> users = userRepo.getUserForSA();

        for(Users user : users)
        {
           List<JournalEntry>  entries = user.getJournalEntries();
          List<String> filterEntries =  entries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
          String entry = String.join(" ",filterEntries);
          String sentiment = sentimentAnallysisService.getSentiment(entry);
          emailService.sendEmail(user.getEmail(),"Sentiment data for last 7 days",sentiment);


        }

    }

}
