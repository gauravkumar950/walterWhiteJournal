package com.journal.walterWhiteJournal.cache;

import com.journal.walterWhiteJournal.Repository.ConfigRepository;
import com.journal.walterWhiteJournal.entity.configEntry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public enum keys{
        text_to_speech_API,
        Quote_API
    }
    @Autowired
    private ConfigRepository configRepository;

    public Map<String,String> APP_CACHE;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<configEntry> listOfApi = configRepository.findAll();
        for(configEntry i:listOfApi){
            APP_CACHE.put(i.getKey(),i.getValue());
        }
    }
}
