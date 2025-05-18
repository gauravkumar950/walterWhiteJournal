package com.journal.walterWhiteJournal.Repository;

import com.journal.walterWhiteJournal.entity.JournalEntry;
import com.journal.walterWhiteJournal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username); // Changed from findByUserName to findByUsername
}
