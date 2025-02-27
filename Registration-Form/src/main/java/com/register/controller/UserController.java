package com.register.controller;

import com.register.dao.UserDao;
import com.register.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    //create a user
    @PostMapping
    public UserDetails createUser(@RequestBody UserDetails userDetails){
        userDetails.setId(UUID.randomUUID().toString());
        return userDao.save(userDetails);
    }

    //get sinngle user
    @GetMapping("/{id}")
    public UserDetails getUser(@PathVariable String id){

        return userDao.get(id);
    }

    //get all user
    @GetMapping
    public Map<Object,Object> getAll(){
        return userDao.findall();
    }

    //delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){

         userDao.delete(id);

    }


}
