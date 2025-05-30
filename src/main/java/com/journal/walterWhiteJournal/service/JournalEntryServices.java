package com.journal.walterWhiteJournal.service;

import com.journal.walterWhiteJournal.Repository.JournalRepository;
import com.journal.walterWhiteJournal.entity.JournalEntry;
import com.journal.walterWhiteJournal.entity.User;
import com.mongodb.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServices {
    @Autowired
    private JournalRepository journalEntryRepo;
    @Autowired
    private UserServices userServices;
    @Autowired
    private RedisServices redisServices;

   public List<JournalEntry> findAllEntries(){
       return journalEntryRepo.findAll();
   }
    @Transactional
    public void saveEntry(JournalEntry newEntry, String userName){
       try {
           User user = userServices.findByUserName(userName);
           JournalEntry saved = journalEntryRepo.save(newEntry);
           user.getJournalEntries().addFirst(saved);
           userServices.saveEntry(user);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }
    public void saveEntry(@RequestBody JournalEntry newEntry){
        journalEntryRepo.save(newEntry);
    }

    @Nullable
    public JournalEntry getById(ObjectId id) {
            JournalEntry journal = journalEntryRepo.findById(id).orElse(null);
            if(journal == null) return null;
            redisServices.set(id.toString(),journal,300l);
            return journal;

    }
    @Transactional
    public ResponseEntity<?> removeById(ObjectId myId, String username) {
        User user = userServices.findByUserName(username);
        try {
            user.getJournalEntries().remove(myId);
            journalEntryRepo.deleteById(myId);
            userServices.saveEntry(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> updateEntry(ObjectId myId, JournalEntry newEntry) {
        JournalEntry old = journalEntryRepo.findById(myId).orElse(null);
        if(old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryRepo.save(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}