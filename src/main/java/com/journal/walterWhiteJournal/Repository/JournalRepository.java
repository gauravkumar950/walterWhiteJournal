package com.journal.walterWhiteJournal.Repository;

import com.journal.walterWhiteJournal.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface JournalRepository extends MongoRepository<JournalEntry, ObjectId> {
}