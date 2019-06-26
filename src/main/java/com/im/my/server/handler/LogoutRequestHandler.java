package com.im.my.server.handler;

import com.im.my.protocol.request.LogoutRequestPacket;
import com.im.my.protocol.response.LogoutResponsePacket;
import com.im.my.model.Session;
import com.im.my.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(LogoutRequestHandler.class);
    public static final LogoutRequestHandler INSTANCE=new LogoutRequestHandler();

    private LogoutRequestHandler ()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, LogoutRequestPacket logoutRequestPacket)
    {
        Session session=SessionUtil.getSession(ctx.channel());
        System.out.println(session.getUserName()+"以登出");
        LogoutResponsePacket resp=new LogoutResponsePacket();
        resp.setSuccess(true);
        resp.setReason("success");
        ctx.writeAndFlush(resp);
        SessionUtil.unBindSession(ctx.channel());
    }
}
