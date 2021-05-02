package com.ks.dbHost.service;

import com.ks.dbHost.model.Login;
import com.ks.dbHost.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class UserService {

    @Autowired
    RedisTemplate<String, Map<String, String>> userRedisTemplate;

    public String insertUser(User user) {
        String key = user.getUserId();
        Map<String, String> userMap = new HashMap<>();
        try {
            userMap.put("name", user.getName());
            userMap.put("address", user.getAddress());
            userMap.put("contact", user.getContact());
            userMap.put("password", user.getPassword());
            userMap.put("email", user.getEmail());
            userMap.put("city", user.getCity());
            if (isPresent(key)) {
                return key + " user id already present";
            }
            userRedisTemplate.opsForValue().set(key, userMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return key + " User id added!!";
    }

    public boolean isPresent(String key) {
        try {
            Map<String, String> map = userRedisTemplate.opsForValue().get(key);
            if (!CollectionUtils.isEmpty(map)) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public User selectUser(String key) {
        User user = new User();
        try {
            Map<String, String> map = userRedisTemplate.opsForValue().get(key);
            if (CollectionUtils.isEmpty(map)) {
                return user;
            }
            user.setUserId(key);
            user.setAddress(map.get("address"));
            user.setCity(map.get("city"));
            user.setContact(map.get("contact"));
            user.setEmail(map.get("email"));
            user.setName(map.get("name"));
            user.setPassword(map.get("password"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public boolean checkLogin(Login login) {
        try {
            String key = login.getId();
            User user = this.selectUser(key);
            if (user.getUserId() == null) {
                return false;
            }
            if (login.getPassword().equals(user.getPassword())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String key){
        try {
            if(isPresent(key)){
                return userRedisTemplate.delete(key);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public List<Map<String, String>> getAllData(){
        List<Map<String, String>> mapList= new ArrayList<>();
        try{
            Set<String> keys = userRedisTemplate.keys("*");
            if(!CollectionUtils.isEmpty(keys)){
                mapList = userRedisTemplate.opsForValue().multiGet(keys);
            }
            return mapList;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return mapList;

    }
}
