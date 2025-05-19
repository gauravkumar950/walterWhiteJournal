package com.journal.walterWhiteJournal.entity;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Data
public class configEntry {
    @Indexed(unique = true)
    @NotNull
    String key;
    String value;
}
