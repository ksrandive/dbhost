package com.ks.dbHost.service;

import com.ks.dbHost.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessPerson {

    @Autowired
    private RedisPersonService redisPersonService;

    public void process(String key, Person person) {
        List<String> list = new ArrayList<>();
        list.add(person.getName()+"_"+person.getAge());
        //adding
        redisPersonService.addToRedis(key, list);
    }
}
