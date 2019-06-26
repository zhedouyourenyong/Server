package com.im.my.server.handler;

import com.im.my.protocol.request.ListGroupMembersRequestPacket;
import com.im.my.protocol.response.ListGroupMembersResponsePacket;
import com.im.my.model.Session;
import com.im.my.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket>
{

    private static final Logger logger= LoggerFactory.getLogger(ListGroupMembersRequestHandler.class);
    public static final ListGroupMembersRequestHandler INSTANCE=new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket)
    {
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        //    未检查groupIdid是否存在
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup)
        {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);

        ctx.channel().writeAndFlush(responsePacket);
    }
}
