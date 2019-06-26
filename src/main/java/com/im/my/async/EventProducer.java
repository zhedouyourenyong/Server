package com.im.my.async;


import com.alibaba.fastjson.JSON;
import com.im.my.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/*
* 同一发送事件到redis
* 队列也可以用BlockingQueue
* 但redis可以被共享
* */
@Component
public class EventProducer
{
    private Logger logger= LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public boolean fireEvent(EventModel event)
    {
        try
        {
            String json= JSON.toJSONString(event);
            String key = RedisKeyUtil.getEventQueueKey();
            stringRedisTemplate.opsForList().leftPush(key,json);
            return true;
        }catch (Exception e)
        {
            logger.error("异步事件发送失败",e.getMessage());
            return false;
        }
    }

}
