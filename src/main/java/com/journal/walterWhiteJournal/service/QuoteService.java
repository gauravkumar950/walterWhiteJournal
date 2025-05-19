package com.journal.walterWhiteJournal.service;

import com.journal.walterWhiteJournal.cache.AppCache;
import com.journal.walterWhiteJournal.entity.Quote;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QuoteService {

    @Autowired
    public AppCache appCache;

    private String apiKey;
    @PostConstruct
    private void init() {
        this.apiKey = appCache.APP_CACHE.get(AppCache.keys.Quote_API.toString());  // safe: appCache is ready
    }
    private final String url = "https://api.api-ninjas.com/v1/quotes";


    private final RestTemplate restTemplate = new RestTemplate();

    public String getQuote() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Quote[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, Quote[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().length > 0) {
            return response.getBody()[0].getQuote();
        } else {
            throw new RuntimeException("Failed to fetch quote: " + response.getStatusCode());
        }
    }
}
