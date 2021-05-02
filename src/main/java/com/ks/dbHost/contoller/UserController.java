package com.ks.dbHost.contoller;


import com.ks.dbHost.model.Login;
import com.ks.dbHost.model.RequestId;
import com.ks.dbHost.model.User;
import com.ks.dbHost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path= "/user", consumes = "application/json", produces = "application/json")
    public String addUser(@RequestBody User user)
    {
        return userService.insertUser(user);
    }

    @GetMapping(path= "/getUser")
    public User getUser(@RequestBody RequestId requestId)
    {
       return userService.selectUser(requestId.getId());
    }

    @GetMapping(path= "/login")
    public String authUser(@RequestBody Login login)
    {
        boolean checkLogin = userService.checkLogin(login);
        if(checkLogin){
            return "Logged in successful !!";
        }else{
            return "Id/Password dose not match!!";
        }
    }

    @GetMapping(path= "/removeId")
    public String removeUser(@RequestBody RequestId requestId)
    {
        boolean userDeleted = userService.deleteUser(requestId.getId());
        if(userDeleted){
            return requestId.getId()+" Deleted Successfully!!";
        }else{
            return requestId.getId()+ " Id dose not found !!";
        }
    }

    @GetMapping(path= "/getAll")
    public List<Map<String, String>> getAllUser()
    {
        return userService.getAllData();
    }
}
