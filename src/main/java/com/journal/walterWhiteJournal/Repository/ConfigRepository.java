package com.journal.walterWhiteJournal.Repository;

import com.journal.walterWhiteJournal.entity.configEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<configEntry, ObjectId> {
}
