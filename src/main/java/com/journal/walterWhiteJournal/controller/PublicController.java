package com.journal.walterWhiteJournal.controller;

import com.journal.walterWhiteJournal.entity.User;
import com.journal.walterWhiteJournal.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    public UserServices UserServices;

    @GetMapping("/health-check")
    public String  healthCheck(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User newUser){
        UserServices.saveNewUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
