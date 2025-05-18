package com.journal.walterWhiteJournal.service;

import com.journal.walterWhiteJournal.entity.Quote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QuoteService {

    @Value("${quote.api.key}")
    private  String apiKey;
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
