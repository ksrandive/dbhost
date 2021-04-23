package com.ks.dbHost.service;

import com.ks.dbHost.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class RedisPersonService {

    @Autowired
    private RedisTemplate<String, List<String>> personRedisTemplate;

    public void addToRedis(String key, List<String> person){
        personRedisTemplate.opsForValue().set(key, person);
        System.out.println("value inserted in redis");
    }

    public List<String> getFromRedis(String key){
        List<String> list = personRedisTemplate.opsForValue().get(key);
        if(list.size() != 0){
            System.out.println(Arrays.toString(list.toArray()));
            return list;
        }else {
            System.out.println("no data found");
            return null;
        }
    }

    public void removeFormRedis(String key){
        personRedisTemplate.opsForValue().set(key, new ArrayList<>());
    }
}
