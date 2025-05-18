package com.journal.walterWhiteJournal.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "journal_entry")
@Data
public class JournalEntry {
    @Id
    public ObjectId id;
    private String title;
    private String content;
    private LocalDate date;

}