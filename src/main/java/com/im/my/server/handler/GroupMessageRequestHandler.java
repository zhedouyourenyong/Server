package com.im.my.server.handler;

import com.im.my.protocol.request.GroupMessageRequestPacket;
import com.im.my.protocol.response.GroupMessageResponsePacket;
import com.im.my.session.Session;
import com.im.my.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket>
{

    private static final Logger logger= LoggerFactory.getLogger(GroupMessageRequestHandler.class);

    public static final GroupMessageRequestHandler INSTANCE=new GroupMessageRequestHandler();

    private GroupMessageRequestHandler()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception
    {
        //检查一下是否有该groupId
        String groupId=msg.getToGroupId();

        Session session= SessionUtil.getSession(ctx.channel());

        GroupMessageResponsePacket resp=new GroupMessageResponsePacket();
        resp.setFromGroupId(groupId);
        resp.setFromUserName(session.getUserId());
        resp.setMessage(msg.getMessage());

        ChannelGroup channels=SessionUtil.getChannelGroup(groupId);
        if(channels!=null)
        {
            channels.writeAndFlush(resp);
            System.out.println(groupId+"群发消息成功");
        }
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        logger.error(cause.getMessage(),cause);
    }
}
