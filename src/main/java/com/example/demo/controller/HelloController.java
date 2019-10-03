package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/hello")
    public String hello(){
        long start=System.currentTimeMillis();
        System.out.println("开始时间："+start);

        redisTemplate.executePipelined(new SessionCallback<Object>() {

            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                for(int i=0;i<10000;i++){
                    redisOperations.opsForValue().set("瓜田李下"+i,i+"");
                }
                return null;
            }
        });

        long end=System.currentTimeMillis();
        System.out.println("结束时间："+end);
        System.out.println("花费时间："+(end-start));

        return "花费时间："+(end-start);
    }

    @RequestMapping("/hello2")
    public String hello2(){
        long start=System.currentTimeMillis();
        System.out.println("开始时间："+start);

        for (int i=0;i<10000;i++){
            redisTemplate.opsForValue().set("海贼王"+i,i+"");
        }

        long end=System.currentTimeMillis();
        System.out.println("结束时间："+end);
        System.out.println("花费时间："+(end-start));

        return "花费时间："+(end-start);
    }
}