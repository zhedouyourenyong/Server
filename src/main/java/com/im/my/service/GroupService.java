package com.im.my.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class GroupService
{
    private final Logger logger= LoggerFactory.getLogger(GroupService.class);

    @Autowired
    StringRedisTemplate redisTemplate;

//    public JSONObject
}
