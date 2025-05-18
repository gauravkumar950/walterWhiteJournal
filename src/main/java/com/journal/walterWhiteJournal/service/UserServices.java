package com.journal.walterWhiteJournal.service;

import com.journal.walterWhiteJournal.Repository.UserRepository;
import com.journal.walterWhiteJournal.entity.User;
import com.mongodb.lang.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserServices {

//    public static final Logger logger = LoggerFactory.getLogger(UserServices.class);

    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private QuoteService quoteService;

   public List<User> findAllUsers(){
       return userRepository.findAll();
   }

   public boolean saveEntry(User newEntry){
       userRepository.save(newEntry);
       return true;
   }
   //we want to now save the user with Username and password in encrypted form(BCryptEncoder)
    private static final PasswordEncoder passEncoder = new BCryptPasswordEncoder();
    public boolean saveNewUser(User user){
        try {
            user.setPassword(passEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
        catch (Exception e){
            log.debug("hahahha");
            log.error("Hello Hello : {}",e.getMessage());
            return false;
        }
    }
   public User findByUserName(String userName){
       return userRepository.findByUsername(userName);
   }

    @Nullable
    public Optional<User> getById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void removeById(ObjectId id) {
        userRepository.deleteById(id);
        
    }
}