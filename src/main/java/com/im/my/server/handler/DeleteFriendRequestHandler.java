package com.im.my.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.im.my.model.Session;
import com.im.my.protocol.request.DeleteFriendRequestPacket;
import com.im.my.protocol.response.AddFriendResponsePacket;
import com.im.my.protocol.response.DeleteFriendResponsePacket;
import com.im.my.service.UserService;
import com.im.my.utils.SessionUtil;
import com.im.my.utils.SpringBeanFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class DeleteFriendRequestHandler extends SimpleChannelInboundHandler<DeleteFriendRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(DeleteFriendRequestHandler.class);
    public static final DeleteFriendRequestHandler INSTANCE=new DeleteFriendRequestHandler();

    private UserService userService;

    private DeleteFriendRequestHandler()
    {
        userService= SpringBeanFactory.getBean(UserService.class);
    }


    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, DeleteFriendRequestPacket packet) throws Exception
    {
        DeleteFriendResponsePacket resp=new DeleteFriendResponsePacket();

        String friendId=packet.getFriendId();
        Session session= SessionUtil.getSession(ctx.channel());
        JSONObject jresp=userService.deleteFriend(session.getUserId(),friendId);
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
}
