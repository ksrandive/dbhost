package com.ks.dbHost.service;

import com.ks.dbHost.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class PersonController {

    @Autowired
    private ProcessPerson processPerson;

    @Autowired
    private RedisPersonService redisPersonService;

    @PostMapping(path= "/person", consumes = "application/json", produces = "application/json")
    public Person addPerson(@RequestBody Person person)
    {
        String id = UUID.randomUUID().toString();
        person.setName(person.getName());
        person.setAge(person.getAge());
        processPerson.process(id, person);
        return person;
    }

    @GetMapping(path= "/get")
    public String getPerson(@RequestBody String id)
    {
        List<String> list = redisPersonService.getFromRedis(id);
        return Arrays.toString(list.toArray());
    }
}
