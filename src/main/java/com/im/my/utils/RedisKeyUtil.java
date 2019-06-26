package com.im.my.utils;


public class RedisKeyUtil
{
    private static String SPLIT = ":";
    private static String EVENTQUEUE = "EVENT_QUEUE";
    private static String FRIEND_LIST="FRIEND_LIST";  //好友列表
    private static String GROUP_LIST="GROUP_LIST";  //群聊组列表
    private static String GROUP_DETAIL="GROUP_DETAIL"; //群聊组中成员列表


    public static String getEventQueueKey ()
    {
        return EVENTQUEUE;
    }

    public static String getFriendListKey(String userId)
    {
        return FRIEND_LIST+SPLIT+userId;
    }

    public static String getGroupListKey(String userId)
    {
        return GROUP_LIST+SPLIT+userId;
    }

    public static String getGroupDetailKey(String groupId)
    {
        return GROUP_DETAIL+SPLIT+groupId;
    }
}
