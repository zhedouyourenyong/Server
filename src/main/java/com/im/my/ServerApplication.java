package com.im.my;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication
{
    /*
    * 心跳与空闲检测，服务端端要区分是客户主动登出还是网络出了问题
    * 添加，删除好友功能
    * 将数据存放在数据库，异步加线程池
    * 敏感词过滤
    * */
    public static void main (String[] args)
    {
        SpringApplication.run(ServerApplication.class, args);
    }
}
