package com.im.my.service;

import com.alibaba.fastjson.JSONObject;
import com.im.my.mapper.dao.UserDao;
import com.im.my.model.User;
import com.im.my.utils.MD5Util;
import com.im.my.utils.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserService
{
    private final Logger logger= LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;
    @Autowired
    StringRedisTemplate redisTemplate;

    public JSONObject register (String userName, String password)
    {
        JSONObject resp=new JSONObject();

        if(StringUtils.isBlank(userName))
        {
            resp.put("error","用户名不能为空");
            return resp;
        }
        if(StringUtils.isBlank(password))
        {
            resp.put("error","密码不能为空");
            return resp;
        }
        if(userDao.selectByName(userName)!=null)
        {
            resp.put("error","用户名已被注册");
            return resp;
        }

        User user=new User();
        user.setName(userName);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(MD5Util.MD5(password + user.getSalt()));

        try
        {
            userDao.addUser(user);
        }catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        resp.put("userId",user.getId());
        return resp;
    }

    public JSONObject valid(String name,String password)
    {
        JSONObject resp=new JSONObject();
        if(StringUtils.isBlank(name))
        {
            resp.put("error","用户名不能为空");
            return resp;
        }
        if(StringUtils.isBlank(password))
        {
            resp.put("error","密码不能为空");
            return resp;
        }

        User user=null;
        try
        {
            user=userDao.selectByName(name);
        }catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        if(user==null)
        {
            resp.put("error","用户名不存在");
            return resp;
        }

        if(!MD5Util.MD5(password + user.getSalt()).equals(user.getPassword()))
        {
            resp.put("msg", "密码不正确");
            return resp;
        }

        resp.put("userId",user.getId());
        return resp;
    }

    public JSONObject addFriend(String userId,String friendId)
    {
        JSONObject resp=new JSONObject();

        if(StringUtils.isBlank(friendId))
        {
            resp.put("status",1);
            resp.put("error","friendId不能为空");
            return resp;
        }

        try
        {
            String userName=userDao.selectById(friendId);
            if(StringUtils.isBlank(userName))
            {
                resp.put("status",1);
                resp.put("error","friendId不存在");
                return resp;
            }

            String friendListKey= RedisKeyUtil.getFriendListKey(userId);
            redisTemplate.opsForSet().add(friendListKey,friendId);
            resp.put("status",0);
        }catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        return resp;
    }

    public JSONObject deleteFriend(String userId,String friendId)
    {
        JSONObject resp=new JSONObject();

        if(StringUtils.isBlank(friendId))
        {
            resp.put("status",1);
            resp.put("error","friendId不能为空");
            return resp;
        }
        try
        {
            String friendListKey= RedisKeyUtil.getFriendListKey(userId);
            Boolean res=redisTemplate.opsForSet().isMember(friendListKey,friendId);

            if(res.equals(false))
            {
                resp.put("status",1);
                resp.put("error","对方不是你的好友");
                return resp;
            }

            redisTemplate.opsForSet().remove(friendListKey,friendId);
            resp.put("status",0);
        }catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        return resp;
    }
}
