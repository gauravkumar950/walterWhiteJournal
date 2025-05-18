package com.journal.walterWhiteJournal.service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@Component
@Slf4j
public class TextToSpeechService {

    @Value("${textToSpeech.api.key}")
    private  String API_KEY;

    public void convertTextToSpeech(String entry) {
        String requestBody = String.format("{\"text\": \"%s\", \"model_id\": \"eleven_multilingual_v2\"}", entry);

        HttpResponse<byte[]> response = Unirest.post("https://api.elevenlabs.io/v1/text-to-speech/JBFqnCBsd6RMkjVDRZzb?output_format=mp3_44100_128")
                .header("Content-Type", "application/json")
                .header("xi-api-key", API_KEY)
                .body(requestBody)
                .asBytes();

        if (response.isSuccess()) {
            try (FileOutputStream fos = new FileOutputStream("output.mp3")) {
                fos.write(response.getBody());
                log.info("MP3 file saved successfully.");
            } catch (IOException e) {
                log.error("Error saving the file: " + e.getMessage());
            }
        } else {
            log.error("Failed: " + response.getStatus() + " - " + response.getStatusText());
        }
    }
}
