package com.journal.walterWhiteJournal.service;

import com.journal.walterWhiteJournal.Repository.UserRepositoryImp;
import com.journal.walterWhiteJournal.entity.JournalEntry;
import com.journal.walterWhiteJournal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SentimentAnalysisService {

    public int performSentimentAnalysis(String journal){
        return 1;
    }
}
