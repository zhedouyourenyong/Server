package com.im.my.utils;

import com.im.my.config.Attributes;
import com.im.my.model.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SessionUtil
{
    //也可以放在redis中
    private static final Map<String, Channel> userIdChannelMap=new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap=new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel)
    {
        userIdChannelMap.put(session.getUserId(),channel);  //将userId与channel映射
        channel.attr(Attributes.SESSION).set(session);  //将session保存在channel用于判断该channel是否登录
    }

    public static void unBindSession (Channel channel)
    {
        if(hasLogin(channel))
        {
            Session session=getSession(channel);
            userIdChannelMap.remove(session.getUserId());  //从map中删除
            channel.attr(Attributes.SESSION).set(null);  //GC
            System.out.println(session + " 退出登录!");
        }
    }

    public static boolean hasLogin (Channel channel)
    {
        return getSession(channel)!=null;
    }

    public static Session getSession (Channel channel)
    {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId)
    {
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup)
    {
        groupIdChannelGroupMap.put(groupId,channelGroup);
    }

    public static ChannelGroup getChannelGroup (String groupId)
    {
        return groupIdChannelGroupMap.get(groupId);
    }

}
