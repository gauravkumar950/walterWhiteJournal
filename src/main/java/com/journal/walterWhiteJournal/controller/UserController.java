package com.journal.walterWhiteJournal.controller;

import com.journal.walterWhiteJournal.Repository.UserRepository;
import com.journal.walterWhiteJournal.entity.JournalEntry;
import com.journal.walterWhiteJournal.entity.User;
import com.journal.walterWhiteJournal.service.JournalEntryServices;
import com.journal.walterWhiteJournal.service.QuoteService;
import com.journal.walterWhiteJournal.service.TextToSpeechService;
import com.journal.walterWhiteJournal.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/user")
@Tag(name = "USER API's",description = "read,update & delete user details")
public class UserController {

    @Autowired
    private UserServices UserServices;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private TextToSpeechService textToSpeechService;

    @GetMapping
    public ResponseEntity<?> pullQuotes(){
        try{
            return new ResponseEntity<>(quoteService.getQuote(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/read_journal")
    public ResponseEntity<?> readJournalAloud(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = UserServices.findByUserName(username);
        if(user!=null && !user.getJournalEntries().isEmpty()){
            textToSpeechService.convertTextToSpeech(user.getJournalEntries().get(0).getContent());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    @Operation(summary = "Update user details")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        //the control has reached here means the user is valid and has passed the authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = UserServices.findByUserName(username);
        if(currentUser != null) {
            currentUser.setUsername(user.getUsername());
            currentUser.setPassword(user.getPassword());
            UserServices.saveEntry(currentUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping
    @Operation(summary = "Delete user account")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = UserServices.findByUserName(username);
        userRepository.deleteById(user.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}