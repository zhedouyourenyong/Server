package com.im.my.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class BeanConfig
{
    @Bean(name = "cachedThreadPool")
    public ExecutorService getCachedThreadPoll ()
    {
        ExecutorService service = Executors.newCachedThreadPool();
        return service;
    }

    @Bean
    public StringRedisTemplate redisTemplate (RedisConnectionFactory redisConnectionFactory)
    {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setEnableTransactionSupport(true); //打开事务支持
        return template;
    }
}