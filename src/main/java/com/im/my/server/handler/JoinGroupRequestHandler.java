package com.im.my.server.handler;

import com.im.my.protocol.request.JoinGroupRequestPacket;
import com.im.my.protocol.response.JoinGroupResponsePacket;
import com.im.my.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket>
{

    private static final Logger logger= LoggerFactory.getLogger(JoinGroupRequestHandler.class);
    public static final JoinGroupRequestHandler INSTANCE=new JoinGroupRequestHandler();

    private JoinGroupRequestHandler()
    {
    }


    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket)
    {
        //检查是否存在该groupId
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());


        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
