package com.journal.walterWhiteJournal.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
public class RedisServiceTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void testSendMail(){
        try {
            redisTemplate.opsForValue().set("gmail", "gaurav620@gmail.com");
            Object mail = redisTemplate.opsForValue().get("name");
             int a = 1;
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
