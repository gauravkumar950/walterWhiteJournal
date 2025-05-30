package com.journal.walterWhiteJournal.scheduler;

import com.journal.walterWhiteJournal.Repository.UserRepositoryImp;
import com.journal.walterWhiteJournal.entity.JournalEntry;
import com.journal.walterWhiteJournal.entity.User;
import com.journal.walterWhiteJournal.service.EmailService;
import com.journal.walterWhiteJournal.service.SentimentAnalysisService;
import org.jetbrains.annotations.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImp userRepositoryImp;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail(){
        List<User> list = userRepositoryImp.getUserForSA();
        for(User user: list){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            //Last 7 days journal entry for current user
            List<String> userJournal = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDate.now().minus(7, ChronoUnit.DAYS)))
                    .map(x->x.getContent())
                    .collect(Collectors.toList());
            String join = String.join("",userJournal);
            int senti = sentimentAnalysisService.performSentimentAnalysis(join);
            String sentiment = senti == 0?"Neutral":senti==1?"Happy":"Sad";
            emailService.sendMail(user.getEmail(),"Sentiment of last 7 Days Journal",sentiment);
        }
    }
}
