package com.journal.walterWhiteJournal.controller;

import com.journal.walterWhiteJournal.entity.JournalEntry;
import com.journal.walterWhiteJournal.entity.User;
import com.journal.walterWhiteJournal.service.JournalEntryServices;
import com.journal.walterWhiteJournal.service.TextToSpeechService;
import com.journal.walterWhiteJournal.service.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryServices journalEntryServices;
    @Autowired
    private UserServices userServices;
    @Autowired
    private TextToSpeechService textToSpeechService;

    @GetMapping
    private ResponseEntity<?> getAllJournalEntries(){
        Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userServices.findByUserName(username);
        if(user!=null && !user.getJournalEntries().isEmpty()){
            return new ResponseEntity<>(user.getJournalEntries(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getEntry")
    private ResponseEntity<?> getJournalEntryByUsername(@PathVariable String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userServices.findByUserName(authentication.getName());
        User user = userServices.findByUserName(username);
        if(!currentUser.getUsername().equals(user.getUsername()))   return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if(user!=null && !user.getJournalEntries().isEmpty()){
            return new ResponseEntity<>(user.getJournalEntries(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private  ResponseEntity<?> createJournalEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            myEntry.setDate(LocalDate.now());
            journalEntryServices.saveEntry(myEntry, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userServices.findByUserName(authentication.getName());
       List<JournalEntry> collect = currentUser.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return new ResponseEntity<>(collect, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userServices.findByUserName(authentication.getName());
        List<JournalEntry> collect = currentUser.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            journalEntryServices.removeById(myId,authentication.getName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{myId}")
    public ResponseEntity<?> updateUsingId(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userServices.findByUserName(authentication.getName());
        List<JournalEntry> collect = currentUser.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            return journalEntryServices.updateEntry(myId,newEntry);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}