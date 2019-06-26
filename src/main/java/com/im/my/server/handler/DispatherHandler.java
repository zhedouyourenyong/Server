package com.im.my.server.handler;

import com.im.my.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.im.my.protocol.command.Command.*;

@ChannelHandler.Sharable
public class DispatherHandler extends SimpleChannelInboundHandler<Packet>
{
    private static final Logger logger= LoggerFactory.getLogger(DispatherHandler.class);
    public static final DispatherHandler INSTANCE=new DispatherHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private DispatherHandler()
    {
        handlerMap=new HashMap<>();
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(SENSOR_REQUEST,SensorDataRequestHandler.INSTANCE);
        handlerMap.put(HEAT_BEAT_REQUEST,HeartBeatRequestHandler.INSTANCE);
        handlerMap.put(ADD_FRIEND_REQUEST,AddFriendRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(DELETE_FRIEND_REQUEST,DeleteFriendRequestHandler.INSTANCE);
        handlerMap.put(SHOW_GROUP_LIST_REQUEST,ShowGroupListRequestHandler.INSTANCE);
        handlerMap.put(SHOW_FRIENDS_LIST_REQUEST,ShowFriendsListRequestHandler.INSTANCE);
        handlerMap.put(SEND_MESSAGE_TO_GROUP_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, Packet msg) throws Exception
    {
        handlerMap.get(msg.getCommand()).channelRead(ctx,msg);
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        logger.error(cause.getMessage());
    }
}
