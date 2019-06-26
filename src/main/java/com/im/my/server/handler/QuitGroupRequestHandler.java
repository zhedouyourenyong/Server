package com.im.my.server.handler;

import com.im.my.protocol.request.QuitGroupRequestPacket;
import com.im.my.protocol.response.QuitGroupResponsePacket;
import com.im.my.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(QuitGroupRequestHandler.class);
    public static final QuitGroupRequestHandler INSTANCE=new QuitGroupRequestHandler();

    private QuitGroupRequestHandler()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket)
    {
        //当某个群聊组为空时的处理
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        //加个群发到群聊组的功能
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(requestPacket.getGroupId());
        responsePacket.setSuccess(true);

        ctx.channel().writeAndFlush(responsePacket);
    }
}
