package com.register.dao;

import com.register.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDao {

    private static final String KEY = "USER";

    private final RedisTemplate<String, Object> redisTemplate;

    public UserDao(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    //save user
    public UserDetails save(UserDetails userDetails){
        redisTemplate.opsForHash().put(KEY,userDetails.getId(),userDetails);
        return userDetails;
    }

    //get user by id
    public UserDetails get(String id){
        return (UserDetails) redisTemplate.opsForHash().get(KEY,id);
    }

    //get all users
    public Map<Object,Object> findall(){
        return redisTemplate.opsForHash().entries(KEY);
    }
    //delete user
    public void delete(String id){
        redisTemplate.opsForHash().delete(KEY,id);
    }

}
