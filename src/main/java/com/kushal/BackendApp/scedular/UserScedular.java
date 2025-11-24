package com.kushal.BackendApp.scedular;

import com.kushal.BackendApp.Entities.JournalEntry;
import com.kushal.BackendApp.Entities.Users;
import com.kushal.BackendApp.Enum.Sentiment;
import com.kushal.BackendApp.repo.UserRepo;
import com.kushal.BackendApp.service.EmailService;
import com.kushal.BackendApp.service.SentimentAnallysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SentimentAnallysisService sentimentAnallysisService;


    @Scheduled(cron = "0 0 9 * * SUN")//seconds, minutes, hours, day of months ,month, day of week
    public void fetchUsersAndSendMail()
    {
        List<Users> users = userRepo.getUserForSA();

//        for(Users user : users)
//        {
//            List<JournalEntry>  entries = user.getJournalEntries();
//          List<String> filterEntries =  entries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
//          String entry = String.join(" ",filterEntries);
//          String sentiment = sentimentAnallysisService.getSentiment(entry);
//          emailService.sendEmail(user.getEmail(),"Sentiment data for last 7 days",sentiment);

//        List<Users> users = userRepo.getUserForSA();
        for(Users user : users)
        {
            List<JournalEntry> entries = user.getJournalEntries();
            List<Sentiment> data = entries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> map = new HashMap<>();

            for(Sentiment s : data)
            {
                if(s != null)
                {
                    map.put(s,map.getOrDefault(s,0)+1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;

            for(Map.Entry<Sentiment,Integer> entry : map.entrySet())
            {
                    if(entry.getValue() > maxCount)
                    {
                        maxCount = entry.getValue();
                        mostFrequentSentiment = entry.getKey();
                    }
            }

            if(mostFrequentSentiment != null)
            {
                emailService.sendEmail(user.getEmail(),"sentiment for last 7 days",mostFrequentSentiment.toString());
            }

        }

    }

}
