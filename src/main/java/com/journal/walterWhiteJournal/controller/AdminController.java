package com.journal.walterWhiteJournal.controller;

import com.journal.walterWhiteJournal.entity.User;
import com.journal.walterWhiteJournal.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServices userServices;
    @Autowired


    @GetMapping("/get-allUsers")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers = userServices.findAllUsers();
        if(!allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/save-user")
    public ResponseEntity<?> saveUser(@RequestBody User newUser){
        newUser.getRoles().add("USER");
        userServices.saveNewUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/save-admin")
    public ResponseEntity<?> saveAdmin(@RequestBody User newUser){
        newUser.setRoles(Arrays.asList("USER","ADMIN"));
        userServices.saveNewUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
