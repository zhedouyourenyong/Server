package com.im.my.server.handler;

import com.im.my.protocol.request.MessageRequestPacket;
import com.im.my.protocol.response.MessageResponsePacket;
import com.im.my.model.Session;
import com.im.my.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(MessageRequestHandler.class);

    public static final MessageRequestHandler INSTANCE=new MessageRequestHandler();

    private MessageRequestHandler()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, MessageRequestPacket packet) throws Exception
    {
        Session session= SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());

        Channel toUserChannel=SessionUtil.getChannel(packet.getToUserId());

        if(toUserChannel!=null&&SessionUtil.hasLogin(toUserChannel))
        {
            messageResponsePacket.setMessage(packet.getMessage());
            toUserChannel.writeAndFlush(messageResponsePacket);
        }
        else
        {
            //可以将离线消息存数据库，用户登录后推送过去
            messageResponsePacket.setMessage("对方不在线，发送失败");
            System.out.println("[" + packet.getToUserId() + "] 不在线，发送失败!");
            ctx.writeAndFlush(messageResponsePacket);
        }
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.close();
        logger.error(cause.getMessage(),cause);
    }
}
