package com.journal.walterWhiteJournal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServices {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key,Class<T> entityClass ){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper map = new ObjectMapper();
            return map.readValue(o.toString(),entityClass);
        }
        catch (Exception e){
            log.error("Error in saving cache: "+e.getMessage());
            return null;
        }
    }
    public void set(String key,Object o,Long ttl ){
        try{
            ObjectMapper map = new ObjectMapper();
            String json = map.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,json,ttl, TimeUnit.SECONDS);
        }
        catch (Exception e){
            log.error("Error in saving cache: "+e.getMessage());
        }
    }
}
