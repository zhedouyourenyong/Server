package com.im.my.async;

import com.alibaba.fastjson.JSON;
import com.im.my.utils.RedisKeyUtil;
import com.im.my.utils.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@DependsOn(value = {"springBeanFactory"})
public class EventConsumer
{
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ExecutorService workerGroup= Executors.newFixedThreadPool(4);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void startUp() throws Exception
    {
        initConfig();
        startConsumer();
    }

    private void initConfig ()
    {
        //获取所有的EventHandler，建立EventType与EventHandler的映射
        Map<String, EventHandler> beans = SpringBeanFactory.getBeansOfType(EventHandler.class);
        if(beans != null)
        {
            for (Map.Entry<String, EventHandler> entrys : beans.entrySet())
            {
                List<EventType> types = entrys.getValue().getSupportEventTypes();
                for (EventType type : types)
                {
                    if(!config.containsKey(type))
                    {
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entrys.getValue());
                }
            }
        }
    }

    private void startConsumer()
    {
        //boss不断的从redis中获取事件，然后交给workerGroup处理，自己并不做处理
        String key = RedisKeyUtil.getEventQueueKey();
        Thread boss = new Thread(()->{
            while (true)
            {
                try
                {
                    if(!stringRedisTemplate.hasKey(key))
                        continue;
                    String event = stringRedisTemplate.opsForList().rightPop(key,0,TimeUnit.SECONDS);
                    dispather(event);
                }catch (Exception e)
                {
                    logger.error(e.getMessage());
                    break;
                }
            }
        });
        boss.start();
    }

    private void dispather(String event)
    {
        workerGroup.submit(new Worker(event));
    }


    private class Worker implements Runnable
    {
        private String event;
        public Worker(String event)
        {
            this.event=event;
        }

        @Override
        public void run ()
        {
            try
            {
                EventModel eventModel = JSON.parseObject(event, EventModel.class);
                if(config.containsKey(eventModel.getType()))
                {
                    for (EventHandler handler : config.get(eventModel.getType()))
                    {
                        handler.doHandle(eventModel);
                    }
                }
                else
                {
                    logger.error("事件消费者不能识别的事件:"+eventModel.getType());
                }
            }catch (Exception e)
            {
                logger.error(e.getMessage(),e);
            }
        }
    }
}
