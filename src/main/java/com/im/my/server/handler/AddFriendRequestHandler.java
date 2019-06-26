package com.im.my.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.im.my.model.Session;
import com.im.my.protocol.request.AddFriendRequestPacket;
import com.im.my.protocol.response.AddFriendResponsePacket;
import com.im.my.service.UserService;
import com.im.my.utils.SessionUtil;
import com.im.my.utils.SpringBeanFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class AddFriendRequestHandler extends SimpleChannelInboundHandler<AddFriendRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(AddFriendRequestHandler.class);
    public static final AddFriendRequestHandler INSTANCE=new AddFriendRequestHandler();

    private UserService userService;

    private AddFriendRequestHandler()
    {
        userService= SpringBeanFactory.getBean(UserService.class);
    }


    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, AddFriendRequestPacket packet) throws Exception
    {
        AddFriendResponsePacket resp=new AddFriendResponsePacket();

        String friendId=packet.getFriendId();
        Session session= SessionUtil.getSession(ctx.channel());
        JSONObject jresp=userService.addFriend(session.getUserId(),friendId);
        if((int)jresp.get("status")!=0)
        {
            resp.setSuccess(false);
            resp.setReason((String)jresp.get("error"));
            ctx.writeAndFlush(resp);
        }
        else
        {
            resp.setSuccess(true);
            ctx.writeAndFlush(resp);
        }
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        logger.error(cause.getMessage());
    }
}
