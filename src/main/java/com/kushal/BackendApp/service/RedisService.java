package com.kushal.BackendApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kushal.BackendApp.apiResponse.WeatherResponse;
import io.netty.util.internal.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass)
    {
        try {
            Object obj = redisTemplate.opsForValue().get(key);//return null if key is not present
            if(obj == null)
            {
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(obj.toString(),entityClass);
        }
        catch(Exception e)
        {
            log.error("Exception ",e);
             return null;
        }

    }

    public void set(String key, Object o , Long expiry) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, expiry, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception ", e);

        }

    }
}
